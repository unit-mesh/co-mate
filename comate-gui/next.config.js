/** @type {import('next').NextConfig} */
require('dotenv').config({ path: '~/.comate/.env' })

module.exports = {
  reactStrictMode: true,
  experimental: {
    serverActions: true,
  },
};
