package com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultySyllabus;

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

public class FacultyViewSyllabusAdapter extends FirebaseRecyclerAdapter<FacultyViewSyllabusModel, FacultyViewSyllabusAdapter.FacultyViewSyllabusViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FacultyViewSyllabusAdapter(@NonNull @NotNull FirebaseRecyclerOptions<FacultyViewSyllabusModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull FacultyViewSyllabusAdapter.FacultyViewSyllabusViewHolder holder, int position, @NonNull @NotNull FacultyViewSyllabusModel model) {
        holder.pdfName.setText(model.File_Name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.pdfName.getContext(), FacultyViewAcademicSyllabusWebview.class);
                intent.putExtra("filename", model.getFile_Name());
                intent.putExtra("fileUrl", model.getFile_Url());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public FacultyViewSyllabusViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acadmic_calendar_single_row, parent, false);
        return new FacultyViewSyllabusViewHolder(view);
    }

    public class FacultyViewSyllabusViewHolder extends RecyclerView.ViewHolder {

        TextView pdfName;
        ImageView pdfImage;

        public FacultyViewSyllabusViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pdfName = itemView.findViewById(R.id.pdf_name);
            pdfImage = itemView.findViewById(R.id.pdf_image);
        }
    }
}
