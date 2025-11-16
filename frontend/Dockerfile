# ---- Stage 1: Build the Vite app ----
FROM node:22-alpine AS build

WORKDIR /app

# Copy package files first (better layer caching)
COPY package*.json ./

# Use npm ci for deterministic builds
RUN npm ci

# Copy source code
COPY . .

# Build args
ARG VITE_BACKEND_AUTH_URL
ARG VITE_BACKEND_URL

ENV VITE_BACKEND_AUTH_URL=$VITE_BACKEND_AUTH_URL
ENV VITE_BACKEND_URL=$VITE_BACKEND_URL

# Build
RUN npm run build

# ---- Stage 2: Serve with Nginx ----
FROM nginx:stable-alpine

# Copy built files
COPY --from=build /app/dist /usr/share/nginx/html

# Copy nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Set proper permissions
RUN chown -R nginx:nginx /usr/share/nginx/html && \
    chmod -R 755 /usr/share/nginx/html

EXPOSE 80

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s \
    CMD wget --quiet --tries=1 --spider http://localhost/ || exit 1

CMD ["nginx", "-g", "daemon off;"]