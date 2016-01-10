package md.fusionworks.sensorscanner.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import md.fusionworks.sensorscanner.R;
import md.fusionworks.sensorscanner.activities.ScansActivity;
import md.fusionworks.sensorscanner.data.ScansDataView;
import md.fusionworks.sensorscanner.engine.FileOperations;
import md.fusionworks.sensorscanner.engine.Serialization;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ScanViewHolder> {

    private List<ScansDataView> listScans;

    public ScanAdapter(List<ScansDataView> listScans) {
        this.listScans = listScans;
    }

    @Override
    public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tour_layout, parent, false);
        return new ScanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScanViewHolder holder, final int position) {
        ScansDataView scansDataView = listScans.get(position);
        holder.vDate.setVisibility(View.INVISIBLE);
        holder.vName.setText(scansDataView.getNameListByKey(ScansActivity.getTourName()).get(0));
        holder.vDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScansDataView scansDataView1 = (ScansDataView) Serialization.deserExternalData(Serialization.SCANS_FILE);
                String tmpName = scansDataView1.getNameListByKey(ScansActivity.getTourName()).get(position);
                scansDataView1.removeNameByKey(ScansActivity.getTourName(), tmpName);
                listScans.remove(position);
                Log.d("REM", "File removed: " + FileOperations.removeFile(ScansActivity.getTourName(), tmpName));
                notifyDataSetChanged();
                Serialization.serExternalData(Serialization.SCANS_FILE, scansDataView1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listScans.size();
    }

    public static class ScanViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected ImageView vDeleteImage;
        protected TextView vDate;


        public ScanViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vDeleteImage = (ImageView) v.findViewById(R.id.delete_image);
            vDate = (TextView) v.findViewById(R.id.date_text);
        }
    }


}
