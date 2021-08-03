package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewFaculty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

public class ViewFacultyRecyclerAdapter extends FirebaseRecyclerAdapter<ViewFacultyModel, ViewFacultyRecyclerAdapter.RecyclerViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewFacultyRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ViewFacultyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ViewFacultyRecyclerAdapter.RecyclerViewHolder holder, int position, @NonNull @NotNull ViewFacultyModel model) {
        holder.firstName.setText(model.getFirst_Name());
        holder.middleName.setText(model.getMiddle_Name());
        holder.lastName.setText(model.getLast_Name());
        holder.facultyId.setText(model.getFaculty_Id());
        holder.department.setText(model.getFaculty_Department());
        holder.email.setText(model.getEmail_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,
                        new FacultyDetailsFragment(model.getFirst_Name(), model.getMiddle_Name(),model.getLast_Name(), model.getPhone_No(),
                                model.getAdhar_No(), model.getFaculty_Id(), model.getEmail_id(), model.getFaculty_Address(),
                                model.getFaculty_Password(), model.getDob(), model.getDate_of_join(), model.getFaculty_Gender(),
                                model.getFaculty_Department(), model.getFaculty_Stream(), model.getFaculty_Status(), model.getFaculty_Caste(),
                                  model.getFaculty_Nationality())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_faculty_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();

    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, middleName, lastName, facultyId, department, email;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.faculty_Firstname);
            middleName = itemView.findViewById(R.id.faculty_MiddleName);
            lastName = itemView.findViewById(R.id.faculty_Lastname);
            facultyId = itemView.findViewById(R.id.faculty_id);
            department = itemView.findViewById(R.id.faculty_department);
            email = itemView.findViewById(R.id.faculty_email);
        }
    }
}
