package gregory.dan.licenceorganiser.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gregory.dan.licenceorganiser.R;
import gregory.dan.licenceorganiser.Unit.Unit;

/**
 * Created by Daniel Gregory on 26/08/2018.
 */
public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.UnitViewHolder>{

    private List<Unit> units;
    private ListItemClickListener mListItemClickListener;

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.unit_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
        if(units == null) {
            return;
        }
        String unitTitle = units.get(position).unitTitle;
        holder.unitTitleText.setText(unitTitle);
    }

    @Override
    public int getItemCount() {
        if(units != null){
            return units.size();
        }else{
            return 0;
        }
    }

    public interface ListItemClickListener{
        void onClick(int item);
    }

    public UnitRecyclerViewAdapter(ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public class UnitViewHolder<U extends RecyclerView.ViewHolder> extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView unitTitleText;

        public UnitViewHolder(View itemView) {
            super(itemView);
            unitTitleText = itemView.findViewById(R.id.list_item_unit_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onClick(getAdapterPosition());
        }
    }

    public void setUnits(List<Unit> units){
        this.units = units;
        notifyDataSetChanged();
    }


}
