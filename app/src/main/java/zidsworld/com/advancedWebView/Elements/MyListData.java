package zidsworld.com.advancedWebView.Elements;


public class MyListData {
    private String description;
    private final String title_text;
    private int imgId;
    public MyListData(String description, String title_text, int imgId) {
        this.description = description;
        this.imgId = imgId;
        this.title_text=title_text;
    }
    public String getTitle() {
        return title_text;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getImgId() {
        return imgId;
    } public String getDescription (){
        return description;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}