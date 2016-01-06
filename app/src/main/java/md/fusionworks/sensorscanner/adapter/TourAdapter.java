package md.fusionworks.sensorscanner.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import md.fusionworks.sensorscanner.R;
import md.fusionworks.sensorscanner.ScansActivity;
import md.fusionworks.sensorscanner.data.ScansDataView;
import md.fusionworks.sensorscanner.data.ToursDataView;
import md.fusionworks.sensorscanner.engine.FileOperations;
import md.fusionworks.sensorscanner.engine.Serialization;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    private List<ToursDataView> tourList;

    public TourAdapter(List<ToursDataView> tourList) {
        this.tourList = tourList;
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    @Override
    public void onBindViewHolder(TourViewHolder tourViewHolder, final int i) {
        ToursDataView toursDataView = tourList.get(i);
        tourViewHolder.vName.setText(toursDataView.getNameList().get(0));
        tourViewHolder.vDate.setText(toursDataView.getStringDate(0));

        tourViewHolder.vDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToursDataView toursDataView1 = (ToursDataView) Serialization.deserExternalData(Serialization.TOURS_FILE);
                ScansDataView scansDataView = (ScansDataView) Serialization.deserExternalData(Serialization.SCANS_FILE);
                scansDataView.getNameMap().remove(toursDataView1.getNameList().get(i));

                FileOperations.removeFolder(toursDataView1.getNameList().get(i));

                toursDataView1.getNameList().remove(i);
                toursDataView1.getDateList().remove(i);
                tourList.remove(i);
                notifyDataSetChanged();
                Log.d("CARD", "After remove: " + toursDataView1.getNameList().size());
                Serialization.serExternalData(Serialization.TOURS_FILE, toursDataView1);
                Serialization.serExternalData(Serialization.SCANS_FILE, scansDataView);
                Log.d("SER", "Tours was saved");
            }
        });
    }

    @Override
    public TourViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_tour_layout, viewGroup, false);

        return new TourViewHolder(itemView);
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected ImageView vDeleteImage;
        protected TextView vDate;

        public TourViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vDeleteImage = (ImageView) v.findViewById(R.id.delete_image);
            vDate = (TextView) v.findViewById(R.id.date_text);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToursDataView toursDataView = (ToursDataView) Serialization.deserExternalData(Serialization.TOURS_FILE);
                    Intent intent = new Intent(v.getContext(), ScansActivity.class);
                    intent.putExtra("Tour", toursDataView.getNameList().get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}