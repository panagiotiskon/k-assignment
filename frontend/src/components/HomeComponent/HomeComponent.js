import React, { useState, useEffect } from "react";
import {
    MDBContainer,
    MDBCardBody,
    MDBCard,
    MDBTypography,
    MDBRow,
    MDBCol,
    MDBBtn,
    MDBInput,
    MDBCardImage,
    MDBIcon,

} from "mdb-react-ui-kit";
import ProductService from '../../api/ProductApi';
import './HomeComponent.scss';
import mobile_phone from '../../assets/mobile_phone.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { faShoppingBasket } from '@fortawesome/free-solid-svg-icons';

const HomeComponent = () => {
    const [products, setProducts] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [selectedCompanies, setSelectedCompanies] = useState([]);
    const [minPrice, setMinPrice] = useState("");
    const [maxPrice, setMaxPrice] = useState("");


    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await ProductService.getAllProducts();
                setProducts(data.content);

                const uniqueCompanies = [...new Set(data.content.map(product => product.company))];
                setCompanies(uniqueCompanies);
            } catch (error) {
                console.error("Error fetching products:", error);
            }
        };

        fetchProducts();
    }, []);

    useEffect(() => {
        const fetchProductsByCompany = async () => {
            try {
                const data = await ProductService.getAllProducts({ companies: selectedCompanies });
                setProducts(data.content);
            } catch (error) {
                console.error("Error fetching products by company:", error);
            }
        };

        fetchProductsByCompany();
    }, [selectedCompanies]);

    const fetchFilteredProducts = async () => {
        try {
            const filters = {
                companies: selectedCompanies,
                minPrice: minPrice ? parseFloat(minPrice) : undefined,
                maxPrice: maxPrice ? parseFloat(maxPrice) : undefined,
            };
            console.log("Filters applied:", filters); // Check filter values

            const data = await ProductService.getAllProducts(filters);
            setProducts(data.content);
        } catch (error) {
            console.error("Error fetching filtered products:", error);
        }
    };

    const handleCompanySelect = (company) => {
        setSelectedCompanies(prevSelected =>
            prevSelected.includes(company)
                ? prevSelected.filter(c => c !== company)
                : [...prevSelected, company]
        );
    };

    const handleApplyFilters = () => {
        fetchFilteredProducts();
    };

    return (
        <MDBContainer className="product-page py-5">
            <MDBRow>
                <h6>Φίλτρα</h6>
                <MDBCol md="3" className="left-column">
                    <MDBCard className="filter-card">
                        <label style={{ marginBottom: "2px", fontWeight: "bold" }}>Εταιρεία</label>
                        <ul className="company-list">
                            {companies.map(company => (
                                <li key={company}>
                                    <input
                                        type="checkbox"
                                        checked={selectedCompanies.includes(company)}
                                        onChange={() => handleCompanySelect(company)}
                                    />
                                    <label style={{ marginLeft: "5px" }}>{company}</label>
                                </li>
                            ))}
                        </ul>
                    </MDBCard>
                    <MDBCard className="price-card">
                        <div className="price-range">
                            <label style={{ marginBottom: "5px", fontWeight: "bold" }}>Εύρος Τιμής</label>                            <MDBInput
                                label="€ Min"
                                type="number"
                                value={minPrice}
                                onChange={(e) => setMinPrice(e.target.value)}
                                className="mb-2"
                            />
                            <MDBInput
                                label="€ Max"
                                type="number"
                                value={maxPrice}
                                onChange={(e) => setMaxPrice(e.target.value)}
                                className="mb-2"
                            />
                            <MDBBtn onClick={handleApplyFilters} color="black" className="apply-btn">
                                <FontAwesomeIcon icon={faArrowRight} />
                            </MDBBtn>
                        </div>
                    </MDBCard>
                </MDBCol>

                <MDBCol className="product-container">

                    {products.length > 0 ? (
                        products.map(product => (

                            <MDBCol key={product.id} className="mb-4">
                                <MDBCard className="product-card shadow-sm">
                                    <div className="position-relative">
                                        <span className="badge bg-success position-absolute top-0 start-0 m-2">
                                            Ανακύκλωση €30
                                        </span>
                                    </div>
                                    <MDBCardImage
                                        src={mobile_phone}
                                        className="mx-auto mt-3"
                                        style={{
                                            height: "150px",
                                            maxWidth: "150px",
                                        }}
                                        alt={product.name}
                                    />

                                    <MDBCardBody>
                                        <div className="d-flex justify-content-between align-items-center mb-1">
                                            <span className="text-muted small">{product.sku}</span>
                                            <div className="d-flex align-items-center">
                                                <MDBIcon fas icon="star" className="text-warning me-1" />
                                            </div>
                                        </div>

                                        <MDBTypography tag="h6" className="fw-bold text-start">
                                            {product.name}
                                        </MDBTypography>

                                        <span className="badge bg-success d-block my-2">Άμεσα Διαθέσιμο</span>

                                        <p className="text-muted small text-start mb-2">{product.description}</p>

                                        <div className="text-muted small mb-2">
                                            <p className="mb-1">Επιλογή ώρας & ημέρας</p>
                                            <p>Διαθέσιμο για δωρεάν παράδοση σε 20’</p>
                                        </div>

                                        <div className="d-flex justify-content-between align-items-center">
                                            <p className="fw-bold mb-1 price ">€{product.price}</p>
                                            <MDBBtn color="warning">
                                                <MDBIcon fas icon="shopping-cart" />
                                                <FontAwesomeIcon icon={faShoppingBasket} style={{ color: 'black' }} />
                                            </MDBBtn>
                                        </div>

                                        <div className="form-check mt-3">
                                            <input className="form-check-input" type="checkbox" id={`compare-${product.id}`} />
                                            <label className="form-check-label small text-muted" htmlFor={`compare-${product.id}`}>
                                                Σύγκρινε το
                                            </label>
                                        </div>
                                    </MDBCardBody>
                                </MDBCard>
                            </MDBCol>

                        ))
                    ) : (
                        <MDBCol md="12">
                            <p>No products available.</p>
                        </MDBCol>
                    )}
                </MDBCol>
            </MDBRow>
        </MDBContainer>


    );
};

export default HomeComponent;
