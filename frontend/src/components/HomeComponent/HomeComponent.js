import React, { useState, useEffect, useRef, useCallback } from "react";
import axios from "axios";
import { Modal, Button } from 'react-bootstrap';
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
import AuthService from "../../api/AuthenticationAPI.js";
import ProductService from '../../api/ProductApi';
import './HomeComponent.scss';
import mobile_phone from '../../assets/mobile_phone.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingBasket, faArrowRight, faTimes, faPen, faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import NavBarComponent from "../common/navbar/NavBar.js";
import AddProductModal from "../common/modals/AddProduct.js";
import UpdateProductModal from "../common/modals/UpdateProduct.js";

const HomeComponent = () => {
    const [userRole, setUserRole] = useState("")
    const [products, setProducts] = useState([]);
    const [companies, setCompanies] = useState([]);
    const [selectedCompanies, setSelectedCompanies] = useState([]);
    const [minPrice, setMinPrice] = useState("");
    const [maxPrice, setMaxPrice] = useState("");
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [isLoading, setIsLoading] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [cartItems, setCartItems] = useState([]);
    const [isCartOpen, setIsCartOpen] = useState(false);
    const [userId, setUserId] = useState('');


    const addToCart = (productId) => {
        setCartItems((prevItems) => {
            const itemIndex = prevItems.findIndex((item) => item.productId === productId);
            if (itemIndex !== -1) {
                const updatedItems = [...prevItems];
                updatedItems[itemIndex].quantity += 1;
                return updatedItems;
            } else {
                return [...prevItems, { productId, quantity: 1 }];
            }
        });
    };

    const incrementQuantity = (productId) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.productId === productId ? { ...item, quantity: item.quantity + 1 } : item
            )
        );
    };

    const decrementQuantity = (productId) => {
        setCartItems((prevItems) =>
            prevItems
                .map((item) =>
                    item.productId === productId ? { ...item, quantity: item.quantity - 1 } : item
                )
                .filter((item) => item.quantity > 0) // Remove item if quantity is zero
        );
    };

    const removeItem = (productId) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.productId !== productId));
    };

    const toggleCart = () => {
        setIsCartOpen(!isCartOpen);
    };

    // Submit cart to API
    const submitCart = () => {

        axios
            .post(`http://localhost:8080/app/${userId}/purchases`, { itemsToPurchase: cartItems })
            .then((data) => {
                console.log('Cart submitted:', data);
                // Clear cart after submission
                setCartItems([]);
                setIsCartOpen(false);
            })
            .catch((error) => console.error('Error submitting cart:', error));
    };

    const admin = "ROLE_ADMIN";
    const client = "ROLE_CLIENT";

    const observer = useRef();

    // Initial Fetch of the list of companies and the user role

    useEffect(() => {
        fetchCompanies();
        fetchUserRole();
    }, []);

    // fetch products based on page and filters

    const fetchProducts = async (page = 1, filters = {}) => {
        setIsLoading(true);
        try {
            const data = await ProductService.getAllProducts(filters, page);
            const newProducts = data.content;


            // If this is the first page, reset the products list
            if (page === 1) {
                setProducts(newProducts);
            } else {
                setProducts(prevProducts => [...prevProducts, ...newProducts]);
            }

            setTotalPages(data.page.totalPages);
            setPage(data.page.number + 1);

        } catch (error) {
            console.error("Error fetching products:", error);
        } finally {
            setIsLoading(false);
        }
    };



    useEffect(() => {

        const filters = {
            companies: selectedCompanies,
            minPrice: minPrice ? parseFloat(minPrice) : undefined,
            maxPrice: maxPrice ? parseFloat(maxPrice) : undefined,
        };

        fetchProducts(page, filters);

    }, [page, selectedCompanies, minPrice, maxPrice]);



    const handleApplyFilters = () => {
        // Reset products and page number
        setProducts([]);
        setPage(1);

        // Create filter object with current min and max prices and selected companies
        const filters = {
            companies: selectedCompanies,
            minPrice: minPrice ? parseFloat(minPrice) : undefined,
            maxPrice: maxPrice ? parseFloat(maxPrice) : undefined,
        };


        // Fetch products with updated filters
        fetchProducts(1, filters);
    };

    const lastProductElementRef = useCallback(node => {
        if (isLoading) return;
        if (observer.current) observer.current.disconnect();

        observer.current = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting && page < totalPages) {
                setPage(prevPage => prevPage + 1);
            }
        });

        if (node) observer.current.observe(node);
    }, [isLoading, totalPages, page]);


    const fetchCompanies = async () => {
        try {
            const companyList = await ProductService.getProductCompanies();
            setCompanies(companyList);
        } catch (error) {
            console.error("Error fetching companies:", error);
        }
    };

    const fetchUserRole = async () => {
        try {
            const data = await AuthService.getCurrentUser();
            setUserRole(data.role);
            setUserId(data.id);
        } catch (error) {
            console.error("Error fetching user role:", error);
        }
    };

    const handleCompanySelect = (company) => {
        setPage(1)
        setSelectedCompanies(prevSelected =>
            prevSelected.includes(company)
                ? prevSelected.filter(c => c !== company)
                : [...prevSelected, company]
        );
    };

    const handleDeleteProduct = async (productId) => {
        try {
            await ProductService.deleteProduct(productId);
            setProducts(products.filter(product => product.id !== productId));
        }
        catch (error) {
            console.error("Error deleting product:", error);
            alert("Failed to delete product.");
        }
    }

    const handleClose = () => setIsModalOpen(false);

    const handleOpen = () => setIsModalOpen(true);

    const handleUpdateOpen = (product) => {
        setSelectedProduct(product);
        setIsUpdateModalOpen(true);
    };

    const handleUpdateClose = () => {
        setIsUpdateModalOpen(false);
        setSelectedProduct(null);
        fetchCompanies();
    };

    return (
        <>
            <NavBarComponent />
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
                                <label style={{ marginBottom: "5px", fontWeight: "bold" }}>Εύρος Τιμής</label>
                                <MDBInput
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
                                <div style={{
                                    display: "flex",
                                    alignItems: "center",
                                    justifyContent: "flex-end"
                                }}>
                                    <MDBBtn onClick={handleApplyFilters} color="black" className="apply-btn" >
                                        <FontAwesomeIcon icon={faArrowRight} />
                                    </MDBBtn>
                                </div>
                            </div>
                        </MDBCard>
                        {userRole === admin && (<div
                            style={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                            }}>
                            <MDBBtn onClick={handleOpen}
                                style={{ width: "80%", justifyContent: "center", fontSize: "16px" }}
                                color="black"
                                className="mt-3 d-flex align-items-center mb-2 ">
                                Πρόσθεσε Προϊόν
                            </MDBBtn>
                        </div >)}
                        <AddProductModal
                            show={isModalOpen}
                            handleClose={handleClose}
                            refreshProducts={fetchProducts}
                        />
                        {userRole === client && (<div
                            style={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                            }}>
                            <MDBBtn onClick={toggleCart}
                                style={{ width: "80%", justifyContent: "center", fontSize: "16px" }}
                                color="black"
                                className="mt-3 d-flex align-items-center mb-2 ">
                                Καλάθι
                            </MDBBtn>
                        </div >)}
                        {isCartOpen && <Modal show={isCartOpen} onHide={toggleCart}>
                            <Modal.Header closeButton>
                                <Modal.Title>Δες τα προιόντα</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                {cartItems.length > 0 ? (
                                    <ul className="cart-item-list">
                                        {cartItems.map((item) => (
                                            <li key={item.productId} className="cart-item d-flex justify-content-between align-items-center">
                                                <div className="cart-item-info">
                                                    <p>Κωδικός προϊόντος: {item.productId}</p>
                                                    <p>Ποσότητα: {item.quantity}</p>
                                                </div>
                                                <div className="cart-item-controls">
                                                    <MDBBtn
                                                        color="dark"
                                                        size="sm"
                                                        onClick={() => incrementQuantity(item.productId)}
                                                        className="me-2"
                                                    >
                                                        <FontAwesomeIcon icon={faPlus} />
                                                    </MDBBtn>
                                                    <MDBBtn
                                                        color="dark"
                                                        size="sm"
                                                        onClick={() => decrementQuantity(item.productId)}
                                                        className="me-2"
                                                    >
                                                        <FontAwesomeIcon icon={faMinus} />
                                                    </MDBBtn>
                                                    <MDBBtn
                                                        color="danger"
                                                        size="sm"
                                                        onClick={() => removeItem(item.productId)}
                                                    >
                                                        <FontAwesomeIcon icon={faTimes} />
                                                    </MDBBtn>
                                                </div>
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <p>Το καλάθι είναι άδειο.</p>
                                )}
                            </Modal.Body>
                            <Modal.Footer >
                                <Button variant="secondary" style={{ fontSize: "14px" }} onClick={toggleCart}>Ακύρωση</Button>
                                <Button variant="black" style={{ fontSize: "14px" }} onClick={submitCart}>Αγορά</Button>
                            </Modal.Footer>
                        </Modal>}
                    </MDBCol>
                    <MDBCol className="product-container">
                        {products.length > 0 ? (
                            products.map((product, index) => (
                                <MDBCol key={product.id}
                                    ref={index === products.length - 1 ? lastProductElementRef : null}
                                    className="mb-4">
                                    <MDBCard className="product-card shadow-sm">
                                        <div className="position-relative">
                                            <span className="badge bg-success position-absolute top-0 start-0 m-2">
                                                Ανακύκλωση €30
                                            </span>
                                        </div>
                                        {userRole === admin && (<>
                                            <MDBBtn
                                                style={{
                                                    display: "flex",
                                                    alignSelf: "flex-end",
                                                    maxHeight: "1.5rem",
                                                    marginRight: "0.5rem",
                                                    marginTop: "0.5rem",
                                                }}
                                                className="btn-sm delete-post-btn"
                                                color="danger"
                                                onClick={() => handleDeleteProduct(product.id)}
                                            >
                                                <FontAwesomeIcon icon={faTimes} />
                                            </MDBBtn>

                                            <MDBBtn
                                                style={{
                                                    display: "flex",
                                                    alignSelf: "flex-end",
                                                    maxHeight: "1.5rem",
                                                    marginRight: "0.5rem",
                                                    marginTop: "0.5rem",
                                                    maxWidth: "2.6rem"
                                                }}
                                                className="btn-sm edit-post-btn"
                                                color="warning"
                                                onClick={() => handleUpdateOpen(product)}
                                            >
                                                <FontAwesomeIcon icon={faPen} />
                                            </MDBBtn> </>)}

                                        <UpdateProductModal
                                            show={isUpdateModalOpen}
                                            handleClose={handleUpdateClose}
                                            refreshProducts={fetchProducts}
                                            product={selectedProduct}
                                        />
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

                                            {userRole === client && <div className="d-flex justify-content-between align-items-center">
                                                <p className="fw-bold mb-1 price ">€{product.price}</p>
                                                <MDBBtn color="warning" onClick={() => addToCart(product.id)}>
                                                    <MDBIcon fas icon="shopping-cart" />
                                                    <FontAwesomeIcon icon={faShoppingBasket} style={{ color: 'black' }} />
                                                </MDBBtn>
                                            </div>}

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
                                <p>Κανένα διαθέσιμο προϊόν</p>
                            </MDBCol>
                        )}
                    </MDBCol>
                </MDBRow>
                {isLoading && <p>Loading more products...</p>}
            </MDBContainer >

        </>

    );
};

export default HomeComponent;