import React, { useState } from 'react';
import { Modal, Button, Form, Alert } from 'react-bootstrap';
import ProductService from '../../api/ProductApi';

const AddProductModal = ({ show, handleClose, refreshProducts }) => {
    const [productName, setProductName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [company, setCompany] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleCreateProduct = async () => {
        setErrorMessage(''); 
        if (!productName || !description || !price || !company) {
            setErrorMessage('Please fill out all fields');
            return;
        }

        try {
            await ProductService.createProduct({
                name: productName,
                description,
                price: parseFloat(price),
                company,
            });
            refreshProducts();
            handleClose();
            setProductName('');
            setDescription('');
            setPrice('');
            setCompany('');
        } catch (error) {
            console.error("Error creating product:", error);
            setErrorMessage('Failed to create product. Please try again.');
        }
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Πρόσθεσε Προϊόν</Modal.Title>
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
                            placeholder="Enter product name"
                            isInvalid={!productName && errorMessage}
                        />
                    </Form.Group>

                    <Form.Group controlId="formDescription" className="mt-3">
                        <Form.Label>Περιγραφή</Form.Label>
                        <Form.Control
                            type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            placeholder="Enter description"
                            isInvalid={!description && errorMessage}
                        />
                    </Form.Group>

                    <Form.Group controlId="formPrice" className="mt-3">
                        <Form.Label>Τιμή</Form.Label>
                        <Form.Control
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            placeholder="Enter price"
                            isInvalid={!price && errorMessage}
                        />
                    </Form.Group>

                    <Form.Group controlId="formCompany" className="mt-3">
                        <Form.Label>Εταιρεία</Form.Label>
                        <Form.Control
                            type="text"
                            value={company}
                            onChange={(e) => setCompany(e.target.value)}
                            placeholder="Enter company name"
                            isInvalid={!company && errorMessage}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer >
                <Button variant="secondary" style={{fontSize:"14px"}} onClick={handleClose}>Ακύρωση</Button>
                <Button variant="black" style={{fontSize:"13px"}} onClick={handleCreateProduct}>Επιβεβαίωση</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default AddProductModal;
