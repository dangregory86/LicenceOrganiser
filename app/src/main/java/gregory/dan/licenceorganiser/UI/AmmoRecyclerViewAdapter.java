package gregory.dan.licenceorganiser.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.Unit.Ammunition;

/**
 * Created by Daniel Gregory on 02/09/2018.
 */
public class AmmoRecyclerViewAdapter extends RecyclerView.Adapter<AmmoRecyclerViewAdapter.AmunitionViewHolder> {

    private List<Ammunition> ammo;
    private AmmoRecyclerViewAdapter.ListItemClickListener mListItemClickListener;

    @NonNull
    @Override
    public AmmoRecyclerViewAdapter.AmunitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.ammunition_item_layout;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new AmmoRecyclerViewAdapter.AmunitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmmoRecyclerViewAdapter.AmunitionViewHolder holder, int position) {
        if (ammo == null) {
            return;
        }
        String mAdac = ammo.get(position).adac;
        String mDesignation = ammo.get(position).description;
        String mHCC = ammo.get(position).HCC;
        long mQuantity = ammo.get(position).quantity;


        holder.mAdacTextView.setText(mAdac);
        holder.mDesignationTextView.setText(mDesignation);
        holder.mHCCTextView.setText(mHCC);
        holder.mMaxQtyTextView.setText(String.valueOf(mQuantity));
    }

    @Override
    public int getItemCount() {
        if (ammo != null) {
            return ammo.size();
        } else {
            return 0;
        }
    }

    public interface ListItemClickListener {
        void onClick(int item);
    }

    public AmmoRecyclerViewAdapter(AmmoRecyclerViewAdapter.ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public class AmunitionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ammo_item_layout_adac_text_view)
        TextView mAdacTextView;
        @BindView(R.id.ammo_item_layout_designation_text_view)
        TextView mDesignationTextView;
        @BindView(R.id.ammo_item_layout_hcc_text_view)
        TextView mHCCTextView;
        @BindView(R.id.ammo_item_layout_max_qunatity_text_view)
        TextView mMaxQtyTextView;

        AmunitionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onClick(getAdapterPosition());
        }
    }

    public void setAmmunition(List<Ammunition> ammo) {
        this.ammo = ammo;
        notifyDataSetChanged();
    }


}
