import { configureStore } from "@reduxjs/toolkit";
import metaReducer from "./Metaslice";

export const store = configureStore({
    reducer:{
        meta : metaReducer,
    }
});