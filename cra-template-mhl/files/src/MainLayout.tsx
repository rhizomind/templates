import {Outlet} from "react-router-dom";
import {Navbar} from "react-bootstrap";


export default function MainLayout() {
    return (
        <div className={'container-fluid'}>
            <div className={'content'}>
                <Navbar>

                </Navbar>
                <Outlet />
            </div>
        </div>
    )
}