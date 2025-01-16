package org.lb.songshan_17que.verify17que;

/**
 * 14 为什么禁止开发人员使用 isSuccess 作为变量名？
 */
public class A14BanUsingIsSucessAsBooleanName {
    /**
     * javaBeans规范指明了 对于布尔类型的变量的命名规范，会自带一个is在getter方法前
     * 序列化时容易发生混淆，命名为isSuccess，标准的getter方法是isisSuccess
     */


    public class innerClass {
        private boolean isSuccess;
        private Boolean isSuccessBoxed;
        private boolean success;
        private Boolean successBoxed;

        // 这里使用idea 生成getter setter，选中4个但是生成的只有2对getter、setter
        public boolean isSuccess() {
            return isSuccess;
        }
        public Boolean getSuccessBoxed() {
            return isSuccessBoxed;
        }
        public void setSuccess(boolean success) {
            isSuccess = success;
        }
        public void setSuccessBoxed(Boolean successBoxed) {
            isSuccessBoxed = successBoxed;
        }
    }
}
