package com.pradipsahoo7722gmail.cms.AdminDashboard.ViewStudent;

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

public class ViewStudentRecyclerAdapter extends FirebaseRecyclerAdapter<ViewStudentModel, ViewStudentRecyclerAdapter.RecyclerViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewStudentRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ViewStudentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ViewStudentRecyclerAdapter.RecyclerViewHolder holder, int position, @NonNull @NotNull ViewStudentModel model) {
        holder.firstName.setText(model.getFirst_Name());
        holder.middleName.setText(model.getMiddle_Name());
        holder.lastName.setText(model.getLast_Name());
        holder.roll.setText(model.getRoll_No());
        holder.branch.setText(model.getStudent_Stream());
        holder.email.setText(model.getEmail_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.studentWrapper,
                        new StudentDetailsFragment(model.getFirst_Name(), model.getMiddle_Name(), model.getLast_Name(), model.getPhone_No(),
                                model.getAdhar_No(), model.getRegistration_No(), model.getRoll_No(), model.getEmail_id(), model.getParents_Name(),
                                model.getParents_Contact(), model.getStudent_Address(), model.getStudent_Password(), model.getDob(), model.getStudent_Gender(),
                                model.getStudent_Caste(), model.getStudent_Country(), model.getStudent_Course(), model.getStudent_Stream(), model.getStudent_Year(),
                                model.getStudent_Semester())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();

    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, middleName, lastName, roll, branch, email;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.student_Firstname);
            middleName = itemView.findViewById(R.id.student_MiddleName);
            lastName = itemView.findViewById(R.id.student_Lastname);
            roll = itemView.findViewById(R.id.student_roll);
            branch = itemView.findViewById(R.id.student_branch);
            email = itemView.findViewById(R.id.student_email);

        }
    }
}
