package cn.edu.bjut.sipl.smarteye.data;

/**
 * 识别结果数据实体类
 * Created by gongzp
 * date    on 2018/5/26.
 */

public class IdentifyData {


    /**
     * result : {"fibrinous":"一","protein":"18.40","heat":"98.00","carbo":"2.80","fat":"1.50","comment":"含有多种不饱和脂肪酸和丰富的蛋白质，热量低且营养丰富，适宜减肥期间食用。"}
     * fileurl : http://172.25.17.224/_uploads/photos/1_6.jpg
     * filename : 1_6.jpg
     * error : 0
     * name : 非洲象
     */

    private ResultBean result;
    private String fileurl;
    private String filename;
    private int error;
    private String name;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ResultBean {
        /**
         * fibrinous : 一
         * protein : 18.40
         * heat : 98.00
         * carbo : 2.80
         * fat : 1.50
         * comment : 含有多种不饱和脂肪酸和丰富的蛋白质，热量低且营养丰富，适宜减肥期间食用。
         */

        private String fibrinous;
        private String protein;
        private String heat;
        private String carbo;
        private String fat;
        private String comment;

        public String getFibrinous() {
            return fibrinous;
        }

        public void setFibrinous(String fibrinous) {
            this.fibrinous = fibrinous;
        }

        public String getProtein() {
            return protein;
        }

        public void setProtein(String protein) {
            this.protein = protein;
        }

        public String getHeat() {
            return heat;
        }

        public void setHeat(String heat) {
            this.heat = heat;
        }

        public String getCarbo() {
            return carbo;
        }

        public void setCarbo(String carbo) {
            this.carbo = carbo;
        }

        public String getFat() {
            return fat;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
