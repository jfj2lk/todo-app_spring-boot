import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "@/global.css";
import Home from "@/pages/home/Home";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Signup from "./pages/auth/Signup";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
