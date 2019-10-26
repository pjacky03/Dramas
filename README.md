# Dramas
Dramas Reader

1. 戲劇列表：列出所有資料中的戲劇，並可照關鍵字過濾戲劇。
    * 點某一部劇可進入看該戲劇的「戲劇資訊」頁面。
    * 列表要有該劇的名稱 (name)、評分 (rating)、出版日期 (created_at)、縮圖(thumb)。
    * 需要儲存前次抓取結果，讓這個頁面可以在離線狀態進入 App 也能觀看。
    * 需有依照部分劇名過濾出含有關鍵字的戲劇且 App 在離開後下次開啟依然可顯示在上次搜尋後的狀態。請在整個搜尋戲劇流程操作上盡量優化使用體驗。

2. 戲劇資訊：單純顯示戲劇資料的畫面，需要可透過特定網址直接開啟此頁面。
    * 顯示該劇的縮圖 (thumb)、名稱 (name)、評分 (rating)、出版日期 (created_at)、觀看次數(total_views)
    * 可讓瀏覽器或其他 App 透過 http://www.example.com/dramas/:id (僅為範例) 當 :id 帶入 1 時，開啟資料中 drama_id 為 1 的戲劇。
    (./adb shell am start -W -a android.intent.action.VIEW -d http://www.example.com/dramas/:1 com.example.mydramas)
