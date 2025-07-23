package com.example.filemanager.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filemanager.DataAdapter;
import com.example.filemanager.FileOpener;
import com.example.filemanager.Option;
import com.example.filemanager.OptionAdapter;
import com.example.filemanager.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CardFragment extends Fragment {

    private RecyclerView recyclerView;
    private File rootFile;
    private List<File> fileList;
    private DataAdapter dataAdapter;
    private OptionAdapter optionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        // init views
        recyclerView = view.findViewById(R.id.recyclerView);
        ImageView imgBack = view.findViewById(R.id.ivBack);
        TextView tvPathHolder = view.findViewById(R.id.tvPathHolder);
        List<Option> options = new ArrayList<>(Arrays.asList(
                new Option("Info", R.drawable.baseline_info_24),
                new Option("Rename", R.drawable.baseline_drive_file_rename_outline_24),
                new Option("Delete", R.drawable.baseline_delete_outline_24)));
        optionAdapter = new OptionAdapter(options);

        // get access to memory
        rootFile = Environment.getExternalStorageDirectory();

        // check root folder
        if (getArguments() != null) {
            if (getArguments().getString("path") != null) {
                String data = getArguments().getString("path");
                assert data != null;
                rootFile = new File(data);
            }
        }

        // set path to text view
        tvPathHolder.setText(rootFile.getAbsolutePath());

        // show data to recycler view
        getDataAccess();

        // set on click listener to get back to last page
        imgBack.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (rootFile.equals(Environment.getExternalStorageDirectory())) {
                Toast.makeText(getContext(), "This is a root folder!", Toast.LENGTH_SHORT).show();
            } else {
                String path = rootFile.getAbsolutePath().toLowerCase().substring(0, rootFile.getAbsolutePath().lastIndexOf("/"));
                bundle.putString("path", path);
                CardFragment cardFragment = new CardFragment();
                cardFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .replace(R.id.frameLayout, cardFragment).addToBackStack(null).commit();
            }
        });

        // set click listener to open file or folder
        dataAdapter.setClickListener(new DataAdapter.OnClickListener() {
            @Override
            public void onItemClick(File file) {
                if (file.isDirectory()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", file.getAbsolutePath());
                    CardFragment cardFragment = new CardFragment();
                    cardFragment.setArguments(bundle);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.frameLayout, cardFragment).addToBackStack(null).commit();
                } else {
                    try {
                        FileOpener.openFile(getContext(), file);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onLongClick(File file, int position) {
                showOptionDialog(file, position);
            }
        });
        return view;
    }

    private void showOptionDialog(File file, int position) {
        final Dialog dialog = new Dialog(requireContext());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.option_dialog);
        RecyclerView rvOptions = dialog.findViewById(R.id.rvOptions);
        rvOptions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOptions.setAdapter(optionAdapter);
        dialog.show();
        optionAdapter.setClickListener(position1 -> {
            switch (position1) {
                case 0:
                    showFileInfo(file);
                    dialog.dismiss();
                    break;
                case 1:
                    renameFile(file, position);
                    dialog.dismiss();
                    break;
                case 2:
                    deleteFile(file, position);
                    dialog.dismiss();
                    break;
            }
        });
    }

    private void deleteFile(File file, int position) {
        if (file.exists()) {
            if (file.delete()) {
                fileList.remove(position);
                dataAdapter.setFile(fileList);
                Toast.makeText(getContext(), "The file was deleted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "The file is not exist!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileInfo(File file) {
        AlertDialog.Builder detailDialog = new AlertDialog.Builder(getContext());
        detailDialog.setTitle("Information");

        // adding view to the dialog
        final TextView details = new TextView(getContext());
        detailDialog.setView(details);
        Date lastOpen = new Date(file.lastModified());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String resultInfo = String.format("File name: %s\nFile size: %s\nFile path: %s\nLast modified: %s",
                file.getName(), Formatter.formatShortFileSize(getContext(), file.length()), file.getAbsolutePath(), dateFormat.format(lastOpen));
        details.setText(resultInfo);

        // set attributes
        details.setTextSize(15);
        details.setPadding(90, 20, 50, 5);

        // adding button to dismiss
        detailDialog.setPositiveButton("OK", (dialog2, which) -> dialog2.cancel());
        AlertDialog alertDialog = detailDialog.create();
        alertDialog.show();
    }

    private void renameFile(File file, int position) {
        AlertDialog.Builder renameDialog = new AlertDialog.Builder(getContext());
        renameDialog.setTitle("Rename");

        // adding view to the dialog
        final EditText editText = new EditText(getContext());
        editText.setPadding(90, 50, 50, 50);
        editText.setHint(file.getName());
        renameDialog.setView(editText);
        renameDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        renameDialog.setPositiveButton("OK", (dialog, which) -> {
            File oldFile = new File(file.getAbsolutePath());
            File newFile;
            String newFileName = editText.getText().toString().trim();

            // check file is a folder
            if (!oldFile.isDirectory()) {
                String newFileExt = oldFile.getAbsolutePath().toLowerCase().substring(oldFile.getAbsolutePath().lastIndexOf("."));
                newFile = new File(oldFile.getParent(), newFileName + newFileExt);
            } else {
                newFile = new File(oldFile.getParent(), newFileName);
            }

            // set a new file in the collection to show in adapter
            if (oldFile.renameTo(newFile)) {
                fileList.set(position, newFile);
                dataAdapter.setFile(fileList);
                Toast.makeText(getContext(), "File renamed successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to rename file!", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialogRename = renameDialog.create();
        alertDialogRename.show();
    }

    private void getDataAccess() {
        // permission for android version <= 10 (API 29)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            // check permission is already existed
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
            // show files if the permission is existed
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                displayFiles();
            }
        }

        // permission for android >= 11 (API 30)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // check permission is already existed
            if (!Environment.isExternalStorageManager()) {
                try {
                    Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(uri);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", requireActivity().getPackageName())));
                    requireActivity().startActivityIfNeeded(intent, 101);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    requireActivity().startActivityIfNeeded(intent, 101);
                }
            }
            // show files if permission is existed
            if (Environment.isExternalStorageManager()) {
                displayFiles();
            }
        }
    }

    private ArrayList<File> sortFiles(File file) {
        ArrayList<File> sortedList = new ArrayList<>();
        File[] files = file.listFiles();

        // adding not hidden folders
        assert files != null;
        for (File oneFile : files) {
            if (oneFile.isDirectory() && !oneFile.isHidden()) {
                sortedList.add(oneFile);
            }
        }

        // adding some file types
        for (File oneFile : files) {
            if (oneFile.getName().toLowerCase().endsWith(".jpeg") ||
                    oneFile.getName().toLowerCase().endsWith(".jpg") ||
                    oneFile.getName().toLowerCase().endsWith(".png") ||
                    oneFile.getName().toLowerCase().endsWith(".mp3") ||
                    oneFile.getName().toLowerCase().endsWith(".wav") ||
                    oneFile.getName().toLowerCase().endsWith(".mp4") ||
                    oneFile.getName().toLowerCase().endsWith(".pdf") ||
                    oneFile.getName().toLowerCase().endsWith(".doc") ||
                    oneFile.getName().toLowerCase().endsWith(".apk")) {
                sortedList.add(oneFile);
            }
        }
        return sortedList;
    }

    private void displayFiles() {
        recyclerView.setHasFixedSize(true); // true when RV has match parent its size (wrap content - false)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fileList = new ArrayList<>();
        fileList.addAll(sortFiles(rootFile));
        dataAdapter = new DataAdapter(getContext(), fileList);
        recyclerView.setAdapter(dataAdapter);
    }
}