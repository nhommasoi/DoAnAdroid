package com.example.dtanp.quanlinhansu.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.example.dtanp.quanlinhansu.InfoNhanVienActivity;
import com.example.dtanp.quanlinhansu.NhanVienActivity;
import com.example.dtanp.quanlinhansu.R;
import com.example.dtanp.quanlinhansu.model.NhanVien;
import com.example.dtanp.quanlinhansu.model.PhongBan;
import com.example.dtanp.quanlinhansu.myinterface.TruyenNhanVien;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class FragmentInfoNhanVien extends Fragment {

    TextView txtMa, txtTen,txtNgaySinh,txtGioiTinh,txtPhongBan;
    AlertDialog dialog,dialogXoa;
    EditText edtTen;
    RadioButton radNam,radNu;
    TextView txtNgaySinhDialog;
    Button btnNgaySinh,btnSave;
    Spinner spinner;
    int mYear,mMonth,mDay;
    ImageButton imgSua,imgXoa;
    ImageView imgAnh,imgAnhDialog;
    NhanVien nhanVienInfo;
    NhanVienDAO nhanVienDAO;
    PhongBanDAO phongBanDAO;
    List<PhongBan> listPhongBan;
    ArrayAdapter adapter;
    int positionSpiner;
    ImageButton btnCamera, btnFolder;
    public static final int REQUESTCODE_CAMERA = 1;
    public static final int REQUESTCODE_FOLDER = 2;
    TruyenNhanVien truyenNhanVien;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_nhanvien,container,false);
        txtMa = view.findViewById(R.id.txtMa);
        txtTen = view.findViewById(R.id.txtTen);
        txtNgaySinh = view.findViewById(R.id.txtNgaysinh);
        txtGioiTinh = view.findViewById(R.id.txtGioiTinh);
        txtPhongBan= view.findViewById(R.id.txtPhongBan);
        imgSua= view.findViewById(R.id.btnSua);
        imgXoa = view.findViewById(R.id.btnXoa);
        imgAnh = view.findViewById(R.id.imgAnh);
        nhanVienDAO=new NhanVienDAO(getActivity());
        nhanVienDAO.open();
        phongBanDAO = new PhongBanDAO(getActivity());
        phongBanDAO.open();
        listPhongBan=phongBanDAO.LayDanhSachPhongBan();
        truyenNhanVien = (TruyenNhanVien) getActivity();
        addDialog();
        addDialogXoa();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listPhongBan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTen.setText(nhanVienInfo.getTen());
                txtNgaySinhDialog.setText(nhanVienInfo.getNgaySinh());
                if (nhanVienInfo.getGioiTinh()==1)
                {
                    radNam.setChecked(true);
                }
                else
                {
                    radNu.setChecked(true);
                }
                for (int i=0;i<adapter.getCount();i++)
                {
                    PhongBan phongBan = (PhongBan) adapter.getItem(i);
                    if(phongBan.getMa().toString().equals(nhanVienInfo.getMaPhongBan().toString()))
                    {
                        spinner.setSelection(i);
                        break;
                    }
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVienInfo.getImage(),0,nhanVienInfo.getImage().length);
                imgAnhDialog.setImageBitmap(bitmap);
                dialog.show();
            }
        });
        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogXoa.show();
            }
        });
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NhanVienActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void setDataInfo(NhanVien nhanVien)
    {
        this.nhanVienInfo=nhanVien;
        TachChuoi(nhanVien.getNgaySinh());
        txtMa.setText(nhanVien.getMa());
        txtTen.setText(nhanVien.getTen());
        txtNgaySinh.setText(nhanVien.getNgaySinh());
        txtPhongBan.setText(nhanVien.getMaPhongBan());
        if (nhanVien.getGioiTinh()==1)
        {
            txtGioiTinh.setText("Nam");
        }
        else
        {
            txtGioiTinh.setText("Nữ");
        }
        byte[] hinh = nhanVien.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        imgAnh.setImageBitmap(bitmap);
    }

    public void addDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_add,null);
        imgAnhDialog = view.findViewById(R.id.imgAnhAdd);
        edtTen = view.findViewById(R.id.edtTen);
        btnNgaySinh = view.findViewById(R.id.btnNgaySinh);
        txtNgaySinhDialog = view.findViewById(R.id.txtNgaySinh);
        radNam = view.findViewById(R.id.radnam);
        radNu = view.findViewById(R.id.radnu);
        spinner = view.findViewById(R.id.spinnerPhongBan);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnFolder = view.findViewById(R.id.btnFolder);
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE_FOLDER);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUESTCODE_CAMERA);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUESTCODE_CAMERA);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionSpiner=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        Calendar calendar = Calendar.getInstance();
//        mYear = calendar.get(Calendar.YEAR);
//        mMonth = calendar.get(Calendar.MONTH);
//        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String st = "28/2/2001";
        TachChuoi(st);
        //Toast.makeText(getActivity(),mMonth+"",Toast.LENGTH_SHORT).show();
        btnNgaySinh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {



                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        txtNgaySinhDialog.setText(i2+"/"+ (i1+1)+ "/" + i);

                    }
                },mYear,mMonth-1,mDay);
                datePickerDialog.show();
            }
        });
        builder.setView(view);
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ma = nhanVienInfo.getMa();
                String ten = edtTen.getText().toString();
                int gioitinh;
                if (radNam.isChecked())
                    gioitinh = 1;
                else
                    gioitinh = 2;
                String ngaysinh = txtNgaySinh.getText().toString();
                String maPhongBan = listPhongBan.get(positionSpiner).getMa();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnhDialog.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                NhanVien nhanVien = new NhanVien(ma, ten, maPhongBan, image, gioitinh, ngaysinh);
                boolean flag = nhanVienDAO.SuaNhanVien(nhanVien);
                if (flag == true) {
                    setDataInfo(nhanVien);
                    Toast.makeText(getActivity(), "Sua Thành Công", Toast.LENGTH_SHORT).show();
                    truyenNhanVien.XuLySuaNhanVien();
                } else {

                    Toast.makeText(getActivity(), "Sua Thất Bại", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getActivity(),"Them Thanh Cong",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(),"Cancle",Toast.LENGTH_SHORT).show();
            }
        });
        dialog = builder.create();
    }
    public void TachChuoi(String ngaySinh)
    {
        mDay = 0;
        mYear = 0;
        mMonth=0;
        int loai=1;
        for (int i=0;i<ngaySinh.length();i++)
        {
            if(ngaySinh.charAt(i)!='/')
            {
                if(loai==1)
                {
                    mDay=mDay*10+Integer.parseInt(ngaySinh.charAt(i)+"");
                }
                else if (loai==2)
                {
                    mMonth=mMonth*10+Integer.parseInt(ngaySinh.charAt(i)+"");
                }
                else
                {
                    mYear = mYear*10 + Integer.parseInt(ngaySinh.charAt(i)+"");
                }
            }
            else
            {
                loai++;
            }
        }
    }
    public void addDialogXoa()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.xoa);
        builder.setMessage(R.string.ghichuxoa);
        builder.setPositiveButton(R.string.xacnhan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (nhanVienDAO.XoaNhanVien(nhanVienInfo))
                {
                    Toast.makeText(getActivity(),"Xóa Thành Công!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), NhanVienActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa = builder.create();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUESTCODE_CAMERA == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUESTCODE_CAMERA);
            } else {
                Toast.makeText(getActivity(), "Ban Khong Cap Quyen", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUESTCODE_FOLDER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE_FOLDER);
            } else {
                Toast.makeText(getActivity(), "Ban Khong Cap Quyen", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_CAMERA && resultCode == getActivity().RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnhDialog.setImageBitmap(bitmap);

        }
        if (requestCode == REQUESTCODE_FOLDER && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhDialog.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void HienThiSave()
    {
        btnSave.setVisibility(View.VISIBLE);
    }
}
