package com.example.styleomega;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class ItemAdapter extends FirestoreRecyclerAdapter<Item,ItemAdapter.ItemHolder> {
    private OnItemClickListener listener;


    public ItemAdapter(@NonNull FirestoreRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, final int position, @NonNull final Item model) {
        holder.textViewTitle.setText(model.getName());
        holder.textViewPrice.setText("Rs:"+model.getPrice());
        holder.textViewBrand.setText(model.getBrand());
        Picasso.get().load(model.getImage()).into(holder.imageViewItem);
        holder.textViewDepartment.setText(model.getDepartment());
        holder.textViewCategory.setText(model.getCategory());
        holder.textViewSubCategory.setText(model.getSubCategory());

        /*holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ItemActivity.class);
                intent.putExtra("Name", model.getName());
                intent.putExtra("Price",model.getPrice());
                intent.putExtra("Image",model.getImage());
                context.startActivity(intent);
            }
        });*/
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ItemHolder(v);
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewPrice;
        TextView textViewBrand;
        ImageView imageViewItem;
        TextView textViewDepartment;
        TextView textViewCategory;
        TextView textViewSubCategory;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.text_item_name);
            textViewPrice=itemView.findViewById(R.id.text_total_price);
            textViewBrand=itemView.findViewById(R.id.text_brand);
            imageViewItem=itemView.findViewById(R.id.image_item);
            textViewDepartment=itemView.findViewById(R.id.text_department);
            textViewCategory=itemView.findViewById(R.id.text_category);
            textViewSubCategory=itemView.findViewById(R.id.text_sub_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}

