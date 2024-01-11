package com.android.lamaj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FrameListAdapter extends RecyclerView.Adapter<FrameListAdapter.FrameItemViewHolder> {
    private List<FrameWireshark> frames;
    private Context context;

    public FrameListAdapter(Context context, List<FrameWireshark> frames) {
        this.context = context;
        this.frames = frames;
    }

    @NonNull
    @Override
    public FrameItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_frames, parent, false);
        return new FrameItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameItemViewHolder holder, int position) {
        FrameWireshark frame = frames.get(position);

        String IPSource = frame.getSourceIP();
        String IPDestination = frame.getDestinationIP();
        String protocol = frame.getProtocol();
        int frameId = frame.getID();

        holder.IPSrc.setText(IPSource);
        holder.IPDst.setText(IPDestination);
        holder.Protocol.setText(protocol);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameWireshark clickedFrame = FrameDB.getInstance(context).getFrameById(frameId);

                Intent intent = new Intent(context, FramePayload.class);
                intent.putExtra("IPSrc", clickedFrame.getSourceIP());
                intent.putExtra("IPDst", clickedFrame.getDestinationIP());
                intent.putExtra("Protocol", clickedFrame.getProtocol());
                intent.putExtra("Payload", clickedFrame.getPayload());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return frames.size();
    }

    public class FrameItemViewHolder extends RecyclerView.ViewHolder {
        TextView IPSrc;
        TextView IPDst;
        TextView Protocol;

        public FrameItemViewHolder(@NonNull View itemView) {
            super(itemView);
            IPSrc = itemView.findViewById(R.id.IPsrc);
            IPDst = itemView.findViewById(R.id.IPdst);
            Protocol = itemView.findViewById(R.id.Protocol);
        }
    }

    public void updateData(List<FrameWireshark> newFrames) {
        frames.clear();
        frames.addAll(newFrames);
        notifyDataSetChanged();
    }
}