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

        holder.bindData(frame);
    }

    @Override
    public int getItemCount() {
        return frames.size();
    }

    public class FrameItemViewHolder extends RecyclerView.ViewHolder {
        TextView IPSrc;
        TextView IPDst;
        TextView Protocol;
        Context context;

        public FrameItemViewHolder(@NonNull View itemView) {
            super(itemView);
            IPSrc = itemView.findViewById(R.id.IPsrc);
            IPDst = itemView.findViewById(R.id.IPdst);
            Protocol = itemView.findViewById(R.id.Protocol);
            context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FrameWireshark clickedFrame = frames.get(position);
                        startFramePayloadActivity(clickedFrame);
                    }
                }
            });
        }

        public void bindData(FrameWireshark frame) {
            String IPSource = frame.getSourceIP();
            String IPDestination = frame.getDestinationIP();
            String protocol = frame.getProtocol();

            IPSrc.setText(IPSource);
            IPDst.setText(IPDestination);
            Protocol.setText(protocol);
        }

        private void startFramePayloadActivity(FrameWireshark frame) {
            Intent intent = new Intent(context, FramePayload.class);
            intent.putExtra("IPSrc", frame.getSourceIP());
            intent.putExtra("IPDst", frame.getDestinationIP());
            intent.putExtra("Protocol", frame.getProtocol());
            intent.putExtra("Payload", frame.getPayload());
            context.startActivity(intent);
        }
    }

    public void updateData(List<FrameWireshark> newFrames) {
        frames.clear();
        frames.addAll(newFrames);
        notifyDataSetChanged();
    }
}