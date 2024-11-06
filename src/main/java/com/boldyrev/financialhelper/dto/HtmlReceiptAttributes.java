package com.boldyrev.financialhelper.dto;

import com.boldyrev.financialhelper.model.ReceiptItem;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * HTML receipt attributes;
 *
 * @author Alexandr Boldyrev
 */
@Getter
@Builder
public class HtmlReceiptAttributes {

    private String organization;

    private String organizationAddress;

    private String organizationInn;

    private String receiptDate;

    private List<ReceiptItem> items;

    private double totalSum;

    private double cashTotalSum;

    private double ecashTotalSum;

    private String fn;

    private String fd;

    private String fp;
}
