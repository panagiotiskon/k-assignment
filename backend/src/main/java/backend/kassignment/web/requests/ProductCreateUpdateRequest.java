package backend.kassignment.web.requests;

import jakarta.validation.constraints.NotNull;

public class ProductCreateUpdateRequest {

    private String name;
    private String description;
    private Double price;
    private String company;


    @NotNull(message = "Name can not be null")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @NotNull(message = "price can not be null")
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    @NotNull(message = "company can not be null")
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
}
