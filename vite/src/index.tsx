import AppLayout from "@/components/AppLayout";
import MarketingLayout from "@/components/MarketingLayout";
import "@/global.css";
import Layout from "@/layout";
import Home from "@/pages/home/Home";
import "@/utils/axios-default-config";
import { MantineProvider } from "@mantine/core";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Todos from "./pages/todo/Todos";
import { store } from "./store";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Provider store={store}>
      <MantineProvider>
        <BrowserRouter>
          <Routes>
            <Route element={<Layout />}>
              {/* マーケティングページ */}
              <Route element={<MarketingLayout />}>
                <Route index element={<Home />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/login" element={<Login />} />
              </Route>

              {/* アプリページ */}
              <Route element={<AppLayout />}>
                <Route path="/projects/:id" element={<Todos />} />
              </Route>
            </Route>
          </Routes>
        </BrowserRouter>
      </MantineProvider>
    </Provider>
  </StrictMode>,
);
