/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'standalone',
  reactStrictMode: false,
  webpack: (config) => {
    config.module.rules.push({
      test: /\.svg$/,
      use: ['@svgr/webpack'],
    })

    return config
  },
  compiler: {
    styledComponents: true,
  },
  async redirects() {
    return [
      {
        source: '/history',
        destination: '/history/asset',
        permanent: false,
      },
      {
        source: '/history/follow',
        destination: '/history/follow/following',
        permanent: false,
      },
    ]
  },
  images: {
    domains: ['lh3.googleusercontent.com'],
  },
}

export default nextConfig
