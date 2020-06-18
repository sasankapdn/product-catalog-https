package codes.recursive.cnms.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {


    @Id
    @Column(name = "PRODUCT_ID")
    @NotNull
    @Size(max=500)
    public String product_id;


    @Column(name = "category_id")
    @Size(max=500)
    public  String category_id;

    @Column(name = "list_price")

    @Size(max=500)
    public  String list_price;

    @Column(name = "attribute3")
    @Size(max=500)
    public  String attribute3;

    @Column(name = "created_by")

    @Size(max=500)
    public  String created_by;

    @Column(name = "attribute2")
    @Size(max=500)
    public  String attribute2;

    @Column(name = "last_updated_by")
    @Size(max=500)
    public  String last_updated_by;

    @Column(name = "attribute1")
    @Size(max=500)
    public  String attribute1;

    @Column(name = "object_version_id")

    @Size(max=500)
    public  String object_version_id;

    @Column(name = "min_price")

    @Size(max=500)
    public  String min_price;

    @Column(name = "parent_category_id")
    @Size(max=500)
    public  String parent_category_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "creation_date")
    private Date creation_date = new Date();







    @Column(name = "product_name")

    @Size(max=500)
    public  String product_name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "last_update_date")
    private Date last_update_date = new Date();


    @Column(name = "external_url")
    @Size(max=500)
    public  String external_url;

    @Column(name = "attribute5")
    @Size(max=500)
    public  String attribute5;

    @Column(name = "attribute4")
    @Size(max=500)
    public  String attribute4;

    @Column(name = "attribute_category")
    @Size(max=500)
    public  String attribute_category;

    @Column(name = "product_status")

    @Size(max=500)
    public  String product_status;

    @Column(name = "warranty_period_months")
    @Size(max=500)
    public  String warranty_period_months;

    @Column(name = "cost_price")
    @Size(max=500)
    public  String cost_price;

    @Column(name = "twitter_tag")
    @Size(max=500)
    public  String twitter_tag;




}