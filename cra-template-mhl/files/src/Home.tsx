import {Col, Row, Card, Button} from "react-bootstrap";
import editing from './assets/img/icons/spot-illustrations/21.png';

export default function Home() {
    return (
        <>
            <Card>
                <Card.Body className="overflow-hidden p-lg-6">
                    <Row className="align-items-center justify-content-between">
                        <Col lg={6}>
                            <img src={editing} className="img-fluid" alt="" />
                        </Col>
                        <Col lg={6} className="ps-lg-4 my-5 text-center text-lg-start">
                            <h3 className="text-primary">Rhizomind MHAL template!</h3>
                            <p className="lead">Welcome to must-have-all-libs-template</p>
                            <p>Typescript,React,Bootstrap,Jest,Playwright,npm</p>
                        </Col>
                    </Row>
                </Card.Body>
            </Card>
        </>
    )
}