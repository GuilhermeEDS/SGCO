/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: process.env.NODE_ENV ? 'jit' : undefined,
  content: [
    "./**/*.html",
    "./**/*.js"
  ],
  theme: {
    extend: {},
  },
  variants: {
      extend: {},
    },
  plugins: [],
}

