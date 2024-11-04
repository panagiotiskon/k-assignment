import axios from "axios";

const API_URL = "http://localhost:8080/auth";

axios.defaults.withCredentials = true;

const getAllProducts = (filter = {}, page = 0, size = 10) => {

    const queryParams = new URLSearchParams();

    queryParams.append("page", page);
    queryParams.append("size", size);

    if (filter.companies && Array.isArray(filter.companies)) {
        filter.companies.forEach(company => queryParams.append("company", company));
    }

    if (filter.minPrice !== undefined) {
        queryParams.append("minPrice", filter.minPrice);
    }
    
    if (filter.maxPrice !== undefined) {
        queryParams.append("maxPrice", filter.maxPrice);
    }

    const url = `${API_URL}/products?${queryParams.toString()}`;
    console.log(url);
    return axios
        .get(url)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.error("Error while fetching products");
            throw error;
        });
};

const ProductService = {
    getAllProducts
};

export default ProductService;
