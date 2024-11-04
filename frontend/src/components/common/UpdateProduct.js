import React, { useState, useEffect } from 'react';
import { Modal, Button, Form, Alert } from 'react-bootstrap';
import ProductService from '../../api/ProductApi';
import './Modal.scss'

const UpdateModal = ({ show, handleClose, refreshProducts, product }) => {
    const [productName, setProductName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [company, setCompany] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        if (product) {
            setProductName(product.name);
            setDescription(product.description);
            setPrice(product.price);
            setCompany(product.company);
        }
    }, [product]);

    const handleUpdateProduct = async () => {
        setErrorMessage('');
        if (!productName || !description || !price || !company) {
            setErrorMessage('Συμπληρώστε τουλάχιστον τα πεδία');
            return;
        }

        try {
            await ProductService.updateProduct(product.id, {
                name: productName || product.name,
                description: description || product.description,
                price: price !== "" ? parseFloat(price) : product.price,
                company: company || product.company,
            });
            refreshProducts();
            handleClose();
            setProductName('');
            setDescription('');
            setPrice('');
            setCompany('');
        } catch (error) {
            console.error("Error updating product:", error);
            setErrorMessage('Failed to update product. Please try again.');
        }
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Επεξεργάσια Προϊόντος</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
                <Form>
                    <Form.Group controlId="formProductName">
                        <Form.Label>Όνομα</Form.Label>
                        <Form.Control
                            type="text"
                            value={productName}
                            onChange={(e) => setProductName(e.target.value)}
                            placeholder="Προσθέστε όνομα"
                        />
                    </Form.Group>

                    <Form.Group controlId="formDescription" className="mt-3">
                        <Form.Label>Περιγραφή</Form.Label>
                        <Form.Control
                            type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            placeholder="Προσθέστε περιγραφή"
                        />
                    </Form.Group>

                    <Form.Group controlId="formPrice" className="mt-3">
                        <Form.Label>Τιμή</Form.Label>
                        <Form.Control
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            placeholder="Προσθέστε τιμή"
                        />
                    </Form.Group>

                    <Form.Group controlId="formCompany" className="mt-3">
                        <Form.Label>Εταιρεία</Form.Label>
                        <Form.Control
                            type="text"
                            value={company}
                            onChange={(e) => setCompany(e.target.value)}
                            placeholder="Προσθέστε εταιρεία"
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" style={{fontSize:"14px"}} onClick={handleClose}>Ακύρωση</Button>
                <Button variant="black" style={{fontSize:"14px"}} onClick={handleUpdateProduct}>Επιβεβαίωση</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default UpdateModal;
