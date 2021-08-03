package com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicNotice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

public class FacultyViewAcademicNoticeAdapter extends FirebaseRecyclerAdapter<FacultyViewAcademicNoticeModel, FacultyViewAcademicNoticeAdapter.FacultyViewAcademicNoticeViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FacultyViewAcademicNoticeAdapter(@NonNull @NotNull FirebaseRecyclerOptions<FacultyViewAcademicNoticeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull FacultyViewAcademicNoticeAdapter.FacultyViewAcademicNoticeViewHolder holder, int position, @NonNull @NotNull FacultyViewAcademicNoticeModel model) {
        holder.pdfName.setText(model.File_Name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.pdfName.getContext(), FacultyNoticeWebView.class);
                intent.putExtra("fileName", model.getFile_Name());
                intent.putExtra("fileUrl", model.getFile_Url());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public FacultyViewAcademicNoticeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acadmic_calendar_single_row, parent, false);
        return new FacultyViewAcademicNoticeViewHolder(view);
    }

    public class FacultyViewAcademicNoticeViewHolder extends RecyclerView.ViewHolder {

        TextView pdfName;
        ImageView pdfImage;

        public FacultyViewAcademicNoticeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pdfName = itemView.findViewById(R.id.pdf_name);
            pdfImage = itemView.findViewById(R.id.pdf_image);
        }
    }
}
