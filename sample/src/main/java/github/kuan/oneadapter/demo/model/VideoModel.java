package github.kuan.oneadapter.demo.model;


import github.kuan.oneadapter.annotations.ItemViewModelTo;
import github.kuan.oneadapter.demo.itemview.ItemViewVideoView;

@ItemViewModelTo(ItemViewVideoView.class)
public class VideoModel {
    public int type;
}
