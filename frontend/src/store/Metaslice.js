import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import api from "../api/api";

export const fetchMetaData = createAsyncThunk(
  "meta/fetchMetadata",
  async (_, { rejectWithValue }) => {
    try {
      const {data: { data: metaData} }= await api.get("/meta");      
      return metaData;
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);

const metaSlice = createSlice({
  name: "meta",
  initialState: {
    metaData: null,
    loading: false,
    error: null,
  },
  reducers: {
    resetMeta: (state) => {
      state.metaData = null;
      state.loading = false;
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchMetaData.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchMetaData.fulfilled, (state, action) => {
        state.loading = false;
        state.metaData = action.payload;
      })
      .addCase(fetchMetaData.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export const {resetMeta} = metaSlice.actions;
export default metaSlice.reducer;
