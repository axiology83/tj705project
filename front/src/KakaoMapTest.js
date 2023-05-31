import React, { useEffect, useState } from "react";
const { kakao } = window;

function KakaoMapTest() {
  const [map, setMap] = useState(null);

  useEffect(() => {
    const container = document.getElementById("kmap");
    const options = { center: new kakao.maps.LatLng(33.450701, 126.570667) };
    const kakaoMap = new kakao.maps.Map(container, options);
    setMap(kakaoMap);
  }, []);

  return (
    <div>
      <div>카카오맵 테스트</div>
      <div id="kmap" style={{ width: "99%", height: "500px" }}></div>
    </div>
  );
}

export default KakaoMapTest;