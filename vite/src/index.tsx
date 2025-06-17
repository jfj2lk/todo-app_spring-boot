import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { MantineProvider } from "@mantine/core";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "@/index.css";
import Home from "@/pages/home/Home";
import Signup from "./pages/auth/Signup";
import Login from "./pages/auth/Login";
import Todos from "./pages/todo/Todos";
import Layout from "@/layout";
import ProtectedRoute from "@/components/ProtectedRoute";
import MarketingLayout from "@/components/MarketingLayout";
import AppLayout from "@/components/AppLayout";
import Labels from "./pages/label/Labels";
import ProjectPage from "./pages/project/ProjectPage";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <MantineProvider>
      <BrowserRouter>
        <Routes>
          <Route path="" element={<Layout />}>
            {/* マーケティングページ */}
            <Route path="" element={<MarketingLayout />}>
              <Route path="/" element={<Home />} />
              <Route path="/signup" element={<Signup />} />
              <Route path="/login" element={<Login />} />
            </Route>
            {/* アプリページ */}
            <Route path="" element={<ProtectedRoute />}>
              <Route path="" element={<AppLayout />}>
                <Route path="/todos" element={<Todos />} />
                <Route path="/labels" element={<Labels />} />
                <Route path="/projects" element={<ProjectPage />} />
              </Route>
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </MantineProvider>
  </StrictMode>,
);
