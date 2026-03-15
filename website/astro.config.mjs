import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

export default defineConfig({
  site: 'https://aldefy.github.io',
  base: '/compose-chip-textfield',
  integrations: [
    starlight({
      title: 'ChipTextField',
      description: 'Material-free chip input for Compose Multiplatform',
      social: {
        github: 'https://github.com/aldefy/compose-chip-textfield',
        'x.com': 'https://x.com/AditLal',
      },
      logo: {
        dark: './src/assets/logo-dark.svg',
        light: './src/assets/logo-light.svg',
        replacesTitle: false,
      },
      customCss: ['./src/styles/custom.css'],
      sidebar: [
        {
          label: 'Getting Started',
          items: [
            { label: 'Installation', link: '/getting-started/installation/' },
            { label: 'Quick Start', link: '/getting-started/quick-start/' },
          ],
        },
        {
          label: 'Guide',
          items: [
            { label: 'Chip Customization', link: '/guide/chip-customization/' },
            { label: 'Suggestions', link: '/guide/suggestions/' },
            { label: 'Validation & Limits', link: '/guide/validation/' },
          ],
        },
        {
          label: 'API Reference',
          items: [
            { label: 'ChipTextField', link: '/api/chip-textfield/' },
            { label: 'ChipScope', link: '/api/chip-scope/' },
            { label: 'ChipTextFieldState', link: '/api/chip-textfield-state/' },
          ],
        },
        {
          label: 'Examples',
          link: '/examples/',
        },
      ],
    }),
  ],
});
