package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Transaction {
    @Getter
    @Setter
    @ToString
    public static class TransactionnReq{
        private String version;
        private Action action;
    }

    @Getter
    @Setter
    @ToString
    public static class TransactionRes{
        private String version;
        private String resultCode;
        private Output output;
    }
}
