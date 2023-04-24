package com.howl.howlstagram_f16.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.howl.howlstagram_f16.R
import kotlinx.android.synthetic.main.activity_search_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class SearchMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_map)

        val mapView = MapView(this)

        val mapViewContainer = map_view as ViewGroup
        mapViewContainer.addView(mapView)

        val marker = MapPOIItem()

        marker.itemName = "참이슬공장"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(37.29156896815805,127.49442918220196)
        //http://apis.map.kakao.com/android/guide/#centerlevel 마커랑 중심 위치 바꿔서 보여주는 카카오 개발자 사이트
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.29156896815805,127.49442918220196),2,true)

        mapView.addPOIItem(marker)
    }
}