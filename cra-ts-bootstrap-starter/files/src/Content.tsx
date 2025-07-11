import {Route, Routes} from 'react-router-dom';
import MainLayout from './MainLayout';
import Home from './Home';

export function Content() {
    return (
        <>
            <Routes>
                <Route element={<MainLayout />}>
                    <Route path="/" element={<Home />} />
                </Route>
            </Routes>
        </>
    );
}
