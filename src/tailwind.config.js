/** @type {import('tailwindcss').Config} */

module.exports = {
  mode: 'jit',
  content: [
    "./**/*.html",
    "./**/*.js",
    "./**/style.tailwind.css"
  ],
  theme: {
    extend: {},
  },
  variants: {
      extend: {},
    },
  plugins: [],
}

