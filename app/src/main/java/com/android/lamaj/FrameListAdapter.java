package com.android.lamaj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FrameListAdapter extends RecyclerView.Adapter<FrameListAdapter.FrameItemViewHolder> {

    private List<FrameWireshark> frames;

    public FrameListAdapter(List<FrameWireshark> frames) {
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

        holder.IPSrc.setText(IPSource);
        holder.IPDst.setText(IPDestination);
        holder.Protocol.setText(protocol);
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