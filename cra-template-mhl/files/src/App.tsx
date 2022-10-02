import React from 'react';
import {BrowserRouter} from "react-router-dom";
import {Content} from "./Content";

function App() {
  return (
      <BrowserRouter basename={process.env.PUBLIC_URL}>
            <Content />
      </BrowserRouter>
  );
}

export default App;
