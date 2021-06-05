package com.diksha.employeedata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diksha.employeedata.ModelClass.Maindata;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    List<Maindata> employeeModels;
    Context context;
    private static int currentPosition = 0;
    private List<Object> recyclerViewItems;

    String type;
    boolean firstclick=false;

    public EmployeeAdapter(Context context, List<Maindata> employeeModels, String type){
        this.context=context;
        this.type=type;
        this.employeeModels=employeeModels;


    }

    public EmployeeAdapter(MainActivity context, List<Object> recyclerViewItems2, String type) {
        this.context=context;
        this.type=type;
        this.recyclerViewItems=recyclerViewItems2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(type.equals("live")) {
            Maindata employeeModel = employeeModels.get(position);
            bindview(employeeModel,holder,position);
        }else{
            Maindata employeeModel = (Maindata) recyclerViewItems.get(position);
            bindview(employeeModel,holder,position);

        }

         }

    @Override
    public int getItemCount() {
        return employeeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout,linearLayoutcollapsed;
        ImageView imageView,imageViewprofile;
        TextView title,name, education,age,desig;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.textView);
            name=itemView.findViewById(R.id.name);
            education=itemView.findViewById(R.id.education);
            age=itemView.findViewById(R.id.agegender);
            desig=itemView.findViewById(R.id.desgnation_place);
            imageView=itemView.findViewById(R.id.profile_image);
            imageViewprofile=itemView.findViewById(R.id.profile_image_small);
            linearLayout=itemView.findViewById(R.id.expanded);
            linearLayoutcollapsed=itemView.findViewById(R.id.collaped);
        }
    }

    void bindview(Maindata employeeModel, ViewHolder holder, int position){

        holder.title.setText(employeeModel.getFirstname()+" "+employeeModel.getLastname());
        holder.name.setText(employeeModel.getFirstname()+" "+employeeModel.getLastname());
        holder.age.setText(employeeModel.getAge()+" "+ employeeModel.getGender());
        holder.desig.setText(employeeModel.getJobholder().getRole()+ " at "+employeeModel.getJobholder().getOrganization());
        holder.education.setText(employeeModel.getEducation().getDegree()+", "+employeeModel.getEducation().getInstitution());
        Picasso.get().load(employeeModel.getPicture()).into(holder.imageView);
        Picasso.get().load(employeeModel.getPicture()).into(holder.imageViewprofile);

        boolean expanded = employeeModel.isExpanded();
        holder.linearLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
        holder.linearLayoutcollapsed.setVisibility(expanded ? View.GONE : View.VISIBLE);
        holder.linearLayout.startAnimation(expanded?
                AnimationUtils.loadAnimation(context, R.anim.slide_down):AnimationUtils.loadAnimation(context, R.anim.slide_up));

        holder.linearLayoutcollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = employeeModel.isExpanded();
                employeeModel.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = employeeModel.isExpanded();
                employeeModel.setExpanded(!expanded);
                notifyItemChanged(position);

            }
        });
    }
}
