package com.example.dtanp.quanlinhansu;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtanp.quanlinhansu.DAO.NhanVienDAO;
import com.example.dtanp.quanlinhansu.DAO.PhongBanDAO;
import com.example.dtanp.quanlinhansu.fragment.FragmentInfoNhanVien;
import com.example.dtanp.quanlinhansu.fragment.FragmentListNhanVien;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.model.PhongBan;
import com.example.dtanp.quanlinhansu.myinterface.TruyenNhanVien;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NhanVienActivity extends Activity implements TruyenNhanVien {

    AlertDialog dialog;
    EditText edtTen;
    RadioButton radNam, radNu;
    TextView txtNgaySinh;
    Button btnNgaySinh;
    Spinner spinner;
    int mYear, mMonth, mDay;
    NhanVienDAO nhanVienDAO;
    FragmentListNhanVien fragmentListNhanVien;
    PhongBanDAO phongBanDAO;
    List<PhongBan> listPhongBan;
    List<NhanVien> listNhanVien;
    ArrayAdapter adapter;
    int positionSpiner;
    ImageButton btnCamera, btnFolder;
    ImageView imgAnh;
    public static final int REQUESTCODE_CAMERA = 1;
    public static final int REQUESTCODE_FOLDER = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        addDialog();

        nhanVienDAO = new NhanVienDAO(this);
        nhanVienDAO.open();
        listNhanVien=nhanVienDAO.LayDanhSachNhanVien();
        fragmentListNhanVien = (FragmentListNhanVien) getFragmentManager().findFragmentById(R.id.fragmentListNhanVien);
        phongBanDAO = new PhongBanDAO(this);
        phongBanDAO.open();
        listPhongBan = phongBanDAO.LayDanhSachPhongBan();
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listPhongBan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        System.out.println("toi crate");


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nhanvien, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.btnadd) {
            dialog.show();
        }
        if (item.getItemId() == R.id.menugioithieu) {
            Intent intent = new Intent(NhanVienActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.btnssearch)
        {

        }
        if (item.getItemId()==R.id.menuPhongBan)
        {
            Intent intent = new Intent(NhanVienActivity.this,PhongBangActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void setDataNhanVien(NhanVien nhanVien) {
        FragmentInfoNhanVien fragmentInfoNhanVien = (FragmentInfoNhanVien) getFragmentManager().findFragmentById(R.id.fragmenInfoNhanVien);
        if (fragmentInfoNhanVien != null && fragmentInfoNhanVien.isInLayout()) {
            fragmentInfoNhanVien.setDataInfo(nhanVien);
        } else {
            Intent intent = new Intent(NhanVienActivity.this, InfoNhanVienActivity.class);
            intent.putExtra("nhanvien", nhanVien);
            startActivity(intent);
        }

    }

    @Override
    public void XuLySuaNhanVien() {

        Intent intent = new Intent(NhanVienActivity.this,NhanVienActivity.class);
        startActivity(intent);
    }

    public void addDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_add, null);
        edtTen = view.findViewById(R.id.edtTen);
        btnNgaySinh = view.findViewById(R.id.btnNgaySinh);
        txtNgaySinh = view.findViewById(R.id.txtNgaySinh);
        radNam = view.findViewById(R.id.radnam);
        radNu = view.findViewById(R.id.radnu);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnFolder = view.findViewById(R.id.btnFolder);
        imgAnh = view.findViewById(R.id.imgAnhAdd);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(NhanVienActivity.this, new String[]{Manifest.permission.CAMERA}, REQUESTCODE_CAMERA);
            }
        });
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(NhanVienActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUESTCODE_FOLDER);
            }
        });
        spinner = view.findViewById(R.id.spinnerPhongBan);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionSpiner = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        btnNgaySinh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(NhanVienActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        txtNgaySinh.setText(i2 + "/" + (i1 + 1) + "/" + i);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        builder.setView(view);
        builder.setPositiveButton(R.string.luu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ma = getMaTuTang();
                String ten = edtTen.getText().toString();
                int gioitinh;
                if (radNam.isChecked())
                    gioitinh = 1;
                else
                    gioitinh = 2;
                String ngaysinh = txtNgaySinh.getText().toString();
                String maPhongBan = listPhongBan.get(positionSpiner).getMa();
                ;
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                NhanVien nhanVien = new NhanVien(ma, ten, maPhongBan, image, gioitinh, ngaysinh);
                boolean flag = nhanVienDAO.ThemNhanVien(nhanVien);
                if (flag == true) {
                    fragmentListNhanVien.AddNhanVien(nhanVien);
                    listNhanVien.add(nhanVien);
                    Toast.makeText(NhanVienActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    edtTen.setText("");
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH);
                    mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    txtNgaySinh.setText(mDay+"/" + mMonth+"/"+mYear);
                    imgAnh.setImageResource(R.drawable.image_icon);
                } else {

                    Toast.makeText(NhanVienActivity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(NhanVienActivity.this, "Cancle", Toast.LENGTH_SHORT).show();
            }
        });
        dialog = builder.create();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUESTCODE_CAMERA == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUESTCODE_CAMERA);
            } else {
                Toast.makeText(NhanVienActivity.this, "Ban Khong Cap Quyen", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUESTCODE_FOLDER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE_FOLDER);
            } else {
                Toast.makeText(NhanVienActivity.this, "Ban Khong Cap Quyen", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnh.setImageBitmap(bitmap);

        }
        if (requestCode == REQUESTCODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getMaTuTang() {


        Calendar calendar = Calendar.getInstance();
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length()<2)
        {
            month = "0"+month;
        }
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length()<2)
        {
            day="0"+day;
        }
        StringBuilder string = new StringBuilder("NV" + day + month);
        List<String> listMaNV = new ArrayList<>();
        int ma = 0;
        if (listNhanVien.size() > 0) {
            for(NhanVien nv : listNhanVien)
            {
                if(nv.getMa().contains(string.toString()))
                {
                    listMaNV.add(nv.getMa());
                }
            }
            if (listMaNV.size() > 0) {
                int[] arr = new int[listMaNV.size()];
                for (int i = 0; i < listMaNV.size(); i++) {
                    int t = Integer.parseInt(listMaNV.get(i).substring(6));
                    arr[i] = t;
                }
                selectionSort(arr);
                for (int i =0 ; i<arr.length; i++)
                {
                    if((i+1)!=arr.length)
                    {
                        if(arr[i+1]-arr[i]!=1)
                        {
                            ma=arr[i]+1;
                            break;
                        }
                    }
                    ma = arr[i]+1;
                }

            } else {
                ma = 1;
            }
        } else {
            ma = 1;
        }
        string.append(ma);
        while (string.length()<10)
        {
            string.insert(6,"0");
        }

        return string.toString();
    }

    public void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;//searching for lowest index
                }
            }
            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }


    }


}

