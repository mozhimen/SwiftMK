package com.mozhimen.uicorektest.recyclerk.mos

import android.view.View
import android.widget.ImageView
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemGrid
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:54
 * @Version 1.0
 */
class RecyclerKItemGrid : RecyclerKItem<Any, RecyclerKItemGrid.MyVHKRecycler>() {
    override fun onBindData(holder: MyVHKRecycler, position: Int) {
        super.onBindData(holder, position)
        holder.imageView.setImageResource(R.mipmap.datak_item_grid)
    }

    override fun getItemLayoutRes() = R.layout.item_recyclerk_grid

    class MyVHKRecycler(view: View) : VHKRecycler(view) {
        val imageView: ImageView = view.findViewById(R.id.datak_item_grid_img)
    }
}
