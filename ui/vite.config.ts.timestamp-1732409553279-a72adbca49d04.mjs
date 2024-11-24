// vite.config.ts
import VueI18nPlugin from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/@intlify+unplugin-vue-i18n@4.0.0_rollup@4.17.2_vue-i18n@9.13.1_vue@3.5.11_typescript@5.6.2__/node_modules/@intlify/unplugin-vue-i18n/lib/vite.mjs";
import path2 from "path";

// src/vite/config-builder.ts
import Vue from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/@vitejs+plugin-vue@5.1.4_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.31.0__fw4nmmvtn5khaaxnxjjngwv6wu/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import VueJsx from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/@vitejs+plugin-vue-jsx@4.0.1_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.3_vchq73hcr7esdbrjnkrbnexi6y/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";
import fs from "fs";
import path from "path";
import GzipPlugin from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/rollup-plugin-gzip@3.1.0_rollup@4.17.2/node_modules/rollup-plugin-gzip/dist-es/index.mjs";
import Icons from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/unplugin-icons@0.19.2_@vue+compiler-sfc@3.5.11_vue-template-compiler@2.7.14/node_modules/unplugin-icons/dist/vite.js";
import { fileURLToPath } from "url";
import { defineConfig } from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.31.0/node_modules/vite/dist/node/index.js";
import { VitePWA } from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/vite-plugin-pwa@0.20.0_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.31.0__w_od3aigf6m73qml222shr3gwkn4/node_modules/vite-plugin-pwa/dist/index.js";
import VueDevTools from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/vite-plugin-vue-devtools@7.3.8_rollup@4.17.2_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1_4xeuz42bnxixxzrqm4qwswrmw4/node_modules/vite-plugin-vue-devtools/dist/vite.mjs";

// src/vite/library-external.ts
import randomstring from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/randomstring@1.2.3/node_modules/randomstring/index.js";
import { viteExternalsPlugin as ViteExternals } from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/vite-plugin-externals@0.6.2_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.31.0_/node_modules/vite-plugin-externals/dist/src/index.js";
import { createHtmlPlugin as VitePluginHtml } from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/vite-plugin-html@3.2.2_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.31.0_/node_modules/vite-plugin-html/dist/index.mjs";
import {
  viteStaticCopy as ViteStaticCopy
} from "file:///workspaces/Halo-2.20.9/ui/node_modules/.pnpm/vite-plugin-static-copy@1.0.6_vite@5.4.1_@types+node@18.13.0_less@4.2.0_sass@1.60.0_terser@5.31.0_/node_modules/vite-plugin-static-copy/dist/index.js";
var setupLibraryExternal = (isProduction, baseUrl, entry) => {
  const staticSuffix = randomstring.generate({
    length: 8,
    charset: "hex"
  });
  const staticTargets = [
    {
      src: `./node_modules/vue/dist/vue.global${isProduction ? ".prod" : ""}.js`,
      dest: "assets/vue",
      rename: `vue.global.${staticSuffix}.js`
    },
    {
      src: `./node_modules/vue-router/dist/vue-router.global${isProduction ? ".prod" : ""}.js`,
      dest: "assets/vue-router",
      rename: `vue-router.global.${staticSuffix}.js`
    },
    {
      src: "./node_modules/axios/dist/axios.min.js",
      dest: "assets/axios",
      rename: `axios.${staticSuffix}.js`
    },
    {
      src: `./node_modules/vue-demi/lib/index.iife.js`,
      dest: "assets/vue-demi",
      rename: `vue-demi.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@vueuse/shared/index.iife.min.js",
      dest: "assets/vueuse",
      rename: `vueuse.shared.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@vueuse/core/index.iife.min.js",
      dest: "assets/vueuse",
      rename: `vueuse.core.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@vueuse/components/index.iife.min.js",
      dest: "assets/vueuse",
      rename: `vueuse.components.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@vueuse/router/index.iife.min.js",
      dest: "assets/vueuse",
      rename: `vueuse.router.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@halo-dev/components/dist/halo-components.iife.js",
      dest: "assets/components",
      rename: `halo-components.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@halo-dev/console-shared/dist/halo-console-shared.iife.js",
      dest: "assets/console-shared",
      rename: `halo-console-shared.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@halo-dev/richtext-editor/dist/rich-text-editor.iife.js",
      dest: "assets/richtext-editor",
      rename: `halo-rich-text-editor.iife.${staticSuffix}.js`
    },
    {
      src: "./node_modules/@halo-dev/api-client/dist/halo-api-client.iife.js",
      dest: "assets/api-client",
      rename: `halo-api-client.iife.${staticSuffix}.js`
    }
  ];
  const injectTags = staticTargets.map((target) => {
    return {
      injectTo: "head",
      tag: "script",
      attrs: {
        src: `${isProduction ? baseUrl : "/"}${target.dest}/${target.rename}`,
        type: "text/javascript"
      }
    };
  }).filter(Boolean);
  return [
    ViteExternals({
      vue: "Vue",
      "vue-router": "VueRouter",
      axios: "axios",
      "@halo-dev/shared": "HaloConsoleShared",
      "@halo-dev/components": "HaloComponents",
      "@vueuse/core": "VueUse",
      "@vueuse/components": "VueUse",
      "@vueuse/router": "VueUse",
      "vue-demi": "VueDemi",
      "@halo-dev/richtext-editor": "RichTextEditor",
      "@halo-dev/api-client": "HaloApiClient"
    }),
    ViteStaticCopy({
      targets: staticTargets
    }),
    VitePluginHtml({
      minify: false,
      inject: {
        tags: injectTags,
        data: {
          entry
        }
      }
    })
  ];
};

// src/vite/config-builder.ts
var __vite_injected_original_import_meta_url = "file:///workspaces/Halo-2.20.9/ui/src/vite/config-builder.ts";
var sharedPlugins = [
  Vue({
    script: {
      defineModel: true
    }
  }),
  VueJsx(),
  GzipPlugin(),
  Icons({
    compiler: "vue3",
    customCollections: {
      core: {
        logo: () => fs.readFileSync("./src/assets/logo.svg", "utf-8")
      }
    }
  }),
  VitePWA({
    manifest: {
      name: "Halo",
      short_name: "Halo",
      description: "Web Client For Halo",
      theme_color: "#fff"
    },
    disable: true
  }),
  VueDevTools()
];
function createViteConfig(options) {
  const isProduction = options.mode === "production";
  const { base, entryFile, port, outDir, plugins } = options;
  const currentFileDir = path.dirname(fileURLToPath(__vite_injected_original_import_meta_url));
  const rootDir = path.resolve(currentFileDir, "../..");
  return defineConfig({
    base,
    plugins: [
      ...sharedPlugins,
      ...setupLibraryExternal(isProduction, base, entryFile),
      ...plugins || []
    ],
    resolve: {
      alias: {
        "@": path.resolve(rootDir, "src"),
        "@console": path.resolve(rootDir, "console-src"),
        "@uc": path.resolve(rootDir, "uc-src")
      }
    },
    server: {
      port,
      fs: {
        strict: isProduction ? true : false
      }
    },
    build: {
      outDir: path.resolve(rootDir, outDir),
      emptyOutDir: true,
      chunkSizeWarningLimit: 2048,
      rollupOptions: {
        output: {
          manualChunks: {
            vendor: [
              "lodash-es",
              "vue-grid-layout",
              "transliteration",
              "vue-draggable-plus",
              "emoji-mart",
              "colorjs.io",
              "jsencrypt",
              "overlayscrollbars",
              "overlayscrollbars-vue",
              "floating-vue"
            ]
          }
        }
      }
    }
  });
}

// vite.config.ts
var __vite_injected_original_dirname = "/workspaces/Halo-2.20.9/ui";
var vite_config_default = ({ mode }) => {
  return createViteConfig({
    base: "/console/",
    entryFile: "/console-src/main.ts",
    port: 3e3,
    outDir: path2.resolve("build/dist/console"),
    mode,
    plugins: [
      VueI18nPlugin({
        include: [path2.resolve(__vite_injected_original_dirname, "./src/locales/*.yaml")]
      })
    ]
  });
};
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiLCAic3JjL3ZpdGUvY29uZmlnLWJ1aWxkZXIudHMiLCAic3JjL3ZpdGUvbGlicmFyeS1leHRlcm5hbC50cyJdLAogICJzb3VyY2VzQ29udGVudCI6IFsiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIi93b3Jrc3BhY2VzL0hhbG8tMi4yMC45L3VpXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCIvd29ya3NwYWNlcy9IYWxvLTIuMjAuOS91aS92aXRlLmNvbmZpZy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vd29ya3NwYWNlcy9IYWxvLTIuMjAuOS91aS92aXRlLmNvbmZpZy50c1wiO2ltcG9ydCBWdWVJMThuUGx1Z2luIGZyb20gXCJAaW50bGlmeS91bnBsdWdpbi12dWUtaTE4bi92aXRlXCI7XG5pbXBvcnQgcGF0aCBmcm9tIFwicGF0aFwiO1xuaW1wb3J0IHsgUGx1Z2luIH0gZnJvbSBcInZpdGVcIjtcbmltcG9ydCB7IGNyZWF0ZVZpdGVDb25maWcgfSBmcm9tIFwiLi9zcmMvdml0ZS9jb25maWctYnVpbGRlclwiO1xuXG5leHBvcnQgZGVmYXVsdCAoeyBtb2RlIH06IHsgbW9kZTogc3RyaW5nIH0pID0+IHtcbiAgcmV0dXJuIGNyZWF0ZVZpdGVDb25maWcoe1xuICAgIGJhc2U6IFwiL2NvbnNvbGUvXCIsXG4gICAgZW50cnlGaWxlOiBcIi9jb25zb2xlLXNyYy9tYWluLnRzXCIsXG4gICAgcG9ydDogMzAwMCxcbiAgICBvdXREaXI6IHBhdGgucmVzb2x2ZShcImJ1aWxkL2Rpc3QvY29uc29sZVwiKSxcbiAgICBtb2RlLFxuICAgIHBsdWdpbnM6IFtcbiAgICAgIFZ1ZUkxOG5QbHVnaW4oe1xuICAgICAgICBpbmNsdWRlOiBbcGF0aC5yZXNvbHZlKF9fZGlybmFtZSwgXCIuL3NyYy9sb2NhbGVzLyoueWFtbFwiKV0sXG4gICAgICB9KSBhcyBQbHVnaW4sXG4gICAgXSxcbiAgfSk7XG59O1xuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCIvd29ya3NwYWNlcy9IYWxvLTIuMjAuOS91aS9zcmMvdml0ZVwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiL3dvcmtzcGFjZXMvSGFsby0yLjIwLjkvdWkvc3JjL3ZpdGUvY29uZmlnLWJ1aWxkZXIudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL3dvcmtzcGFjZXMvSGFsby0yLjIwLjkvdWkvc3JjL3ZpdGUvY29uZmlnLWJ1aWxkZXIudHNcIjtpbXBvcnQgVnVlIGZyb20gXCJAdml0ZWpzL3BsdWdpbi12dWVcIjtcbmltcG9ydCBWdWVKc3ggZnJvbSBcIkB2aXRlanMvcGx1Z2luLXZ1ZS1qc3hcIjtcbmltcG9ydCBmcyBmcm9tIFwiZnNcIjtcbmltcG9ydCBwYXRoIGZyb20gXCJwYXRoXCI7XG5pbXBvcnQgR3ppcFBsdWdpbiBmcm9tIFwicm9sbHVwLXBsdWdpbi1nemlwXCI7XG5pbXBvcnQgSWNvbnMgZnJvbSBcInVucGx1Z2luLWljb25zL3ZpdGVcIjtcbmltcG9ydCB7IGZpbGVVUkxUb1BhdGggfSBmcm9tIFwidXJsXCI7XG5pbXBvcnQgeyBkZWZpbmVDb25maWcsIHR5cGUgUGx1Z2luIH0gZnJvbSBcInZpdGVcIjtcbmltcG9ydCB7IFZpdGVQV0EgfSBmcm9tIFwidml0ZS1wbHVnaW4tcHdhXCI7XG5pbXBvcnQgVnVlRGV2VG9vbHMgZnJvbSBcInZpdGUtcGx1Z2luLXZ1ZS1kZXZ0b29sc1wiO1xuaW1wb3J0IHsgc2V0dXBMaWJyYXJ5RXh0ZXJuYWwgfSBmcm9tIFwiLi9saWJyYXJ5LWV4dGVybmFsXCI7XG5cbmludGVyZmFjZSBPcHRpb25zIHtcbiAgYmFzZTogc3RyaW5nO1xuICBlbnRyeUZpbGU6IHN0cmluZztcbiAgcG9ydDogbnVtYmVyO1xuICBvdXREaXI6IHN0cmluZztcbiAgcGx1Z2lucz86IFBsdWdpbltdO1xuICBtb2RlOiBzdHJpbmc7XG59XG5cbmV4cG9ydCBjb25zdCBzaGFyZWRQbHVnaW5zID0gW1xuICBWdWUoe1xuICAgIHNjcmlwdDoge1xuICAgICAgZGVmaW5lTW9kZWw6IHRydWUsXG4gICAgfSxcbiAgfSksXG4gIFZ1ZUpzeCgpLFxuICBHemlwUGx1Z2luKCkgYXMgUGx1Z2luLFxuICBJY29ucyh7XG4gICAgY29tcGlsZXI6IFwidnVlM1wiLFxuICAgIGN1c3RvbUNvbGxlY3Rpb25zOiB7XG4gICAgICBjb3JlOiB7XG4gICAgICAgIGxvZ286ICgpID0+IGZzLnJlYWRGaWxlU3luYyhcIi4vc3JjL2Fzc2V0cy9sb2dvLnN2Z1wiLCBcInV0Zi04XCIpLFxuICAgICAgfSxcbiAgICB9LFxuICB9KSxcbiAgVml0ZVBXQSh7XG4gICAgbWFuaWZlc3Q6IHtcbiAgICAgIG5hbWU6IFwiSGFsb1wiLFxuICAgICAgc2hvcnRfbmFtZTogXCJIYWxvXCIsXG4gICAgICBkZXNjcmlwdGlvbjogXCJXZWIgQ2xpZW50IEZvciBIYWxvXCIsXG4gICAgICB0aGVtZV9jb2xvcjogXCIjZmZmXCIsXG4gICAgfSxcbiAgICBkaXNhYmxlOiB0cnVlLFxuICB9KSxcbiAgVnVlRGV2VG9vbHMoKSxcbl07XG5cbmV4cG9ydCBmdW5jdGlvbiBjcmVhdGVWaXRlQ29uZmlnKG9wdGlvbnM6IE9wdGlvbnMpIHtcbiAgY29uc3QgaXNQcm9kdWN0aW9uID0gb3B0aW9ucy5tb2RlID09PSBcInByb2R1Y3Rpb25cIjtcblxuICBjb25zdCB7IGJhc2UsIGVudHJ5RmlsZSwgcG9ydCwgb3V0RGlyLCBwbHVnaW5zIH0gPSBvcHRpb25zO1xuXG4gIGNvbnN0IGN1cnJlbnRGaWxlRGlyID0gcGF0aC5kaXJuYW1lKGZpbGVVUkxUb1BhdGgoaW1wb3J0Lm1ldGEudXJsKSk7XG4gIGNvbnN0IHJvb3REaXIgPSBwYXRoLnJlc29sdmUoY3VycmVudEZpbGVEaXIsIFwiLi4vLi5cIik7XG5cbiAgcmV0dXJuIGRlZmluZUNvbmZpZyh7XG4gICAgYmFzZSxcbiAgICBwbHVnaW5zOiBbXG4gICAgICAuLi5zaGFyZWRQbHVnaW5zLFxuICAgICAgLi4uc2V0dXBMaWJyYXJ5RXh0ZXJuYWwoaXNQcm9kdWN0aW9uLCBiYXNlLCBlbnRyeUZpbGUpLFxuICAgICAgLi4uKHBsdWdpbnMgfHwgW10pLFxuICAgIF0sXG4gICAgcmVzb2x2ZToge1xuICAgICAgYWxpYXM6IHtcbiAgICAgICAgXCJAXCI6IHBhdGgucmVzb2x2ZShyb290RGlyLCBcInNyY1wiKSxcbiAgICAgICAgXCJAY29uc29sZVwiOiBwYXRoLnJlc29sdmUocm9vdERpciwgXCJjb25zb2xlLXNyY1wiKSxcbiAgICAgICAgXCJAdWNcIjogcGF0aC5yZXNvbHZlKHJvb3REaXIsIFwidWMtc3JjXCIpLFxuICAgICAgfSxcbiAgICB9LFxuICAgIHNlcnZlcjoge1xuICAgICAgcG9ydCxcbiAgICAgIGZzOiB7XG4gICAgICAgIHN0cmljdDogaXNQcm9kdWN0aW9uID8gdHJ1ZSA6IGZhbHNlLFxuICAgICAgfSxcbiAgICB9LFxuICAgIGJ1aWxkOiB7XG4gICAgICBvdXREaXI6IHBhdGgucmVzb2x2ZShyb290RGlyLCBvdXREaXIpLFxuICAgICAgZW1wdHlPdXREaXI6IHRydWUsXG4gICAgICBjaHVua1NpemVXYXJuaW5nTGltaXQ6IDIwNDgsXG4gICAgICByb2xsdXBPcHRpb25zOiB7XG4gICAgICAgIG91dHB1dDoge1xuICAgICAgICAgIG1hbnVhbENodW5rczoge1xuICAgICAgICAgICAgdmVuZG9yOiBbXG4gICAgICAgICAgICAgIFwibG9kYXNoLWVzXCIsXG4gICAgICAgICAgICAgIFwidnVlLWdyaWQtbGF5b3V0XCIsXG4gICAgICAgICAgICAgIFwidHJhbnNsaXRlcmF0aW9uXCIsXG4gICAgICAgICAgICAgIFwidnVlLWRyYWdnYWJsZS1wbHVzXCIsXG4gICAgICAgICAgICAgIFwiZW1vamktbWFydFwiLFxuICAgICAgICAgICAgICBcImNvbG9yanMuaW9cIixcbiAgICAgICAgICAgICAgXCJqc2VuY3J5cHRcIixcbiAgICAgICAgICAgICAgXCJvdmVybGF5c2Nyb2xsYmFyc1wiLFxuICAgICAgICAgICAgICBcIm92ZXJsYXlzY3JvbGxiYXJzLXZ1ZVwiLFxuICAgICAgICAgICAgICBcImZsb2F0aW5nLXZ1ZVwiLFxuICAgICAgICAgICAgXSxcbiAgICAgICAgICB9LFxuICAgICAgICB9LFxuICAgICAgfSxcbiAgICB9LFxuICB9KTtcbn1cbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiL3dvcmtzcGFjZXMvSGFsby0yLjIwLjkvdWkvc3JjL3ZpdGVcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIi93b3Jrc3BhY2VzL0hhbG8tMi4yMC45L3VpL3NyYy92aXRlL2xpYnJhcnktZXh0ZXJuYWwudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL3dvcmtzcGFjZXMvSGFsby0yLjIwLjkvdWkvc3JjL3ZpdGUvbGlicmFyeS1leHRlcm5hbC50c1wiO2ltcG9ydCByYW5kb21zdHJpbmcgZnJvbSBcInJhbmRvbXN0cmluZ1wiO1xuaW1wb3J0IHR5cGUgeyBIdG1sVGFnRGVzY3JpcHRvciB9IGZyb20gXCJ2aXRlXCI7XG5pbXBvcnQgeyB2aXRlRXh0ZXJuYWxzUGx1Z2luIGFzIFZpdGVFeHRlcm5hbHMgfSBmcm9tIFwidml0ZS1wbHVnaW4tZXh0ZXJuYWxzXCI7XG5pbXBvcnQgeyBjcmVhdGVIdG1sUGx1Z2luIGFzIFZpdGVQbHVnaW5IdG1sIH0gZnJvbSBcInZpdGUtcGx1Z2luLWh0bWxcIjtcbmltcG9ydCB7XG4gIHZpdGVTdGF0aWNDb3B5IGFzIFZpdGVTdGF0aWNDb3B5LFxuICB0eXBlIFRhcmdldCxcbn0gZnJvbSBcInZpdGUtcGx1Z2luLXN0YXRpYy1jb3B5XCI7XG5cbi8qKlxuICogSXQgY29waWVzIHRoZSBleHRlcm5hbCBsaWJyYXJpZXMgdG8gdGhlIGBhc3NldHNgIGZvbGRlciwgYW5kIGluamVjdHMgdGhlIHNjcmlwdCB0YWdzIGludG8gdGhlIEhUTUxcbiAqXG4gKiBAcGFyYW0ge2Jvb2xlYW59IGlzUHJvZHVjdGlvbiAtIGJvb2xlYW5cbiAqIEBwYXJhbSB7c3RyaW5nfSBiYXNlVXJsIC0gVGhlIGJhc2UgdXJsIG9mIHRoZSBhcHBsaWNhdGlvbi5cbiAqIEByZXR1cm5zIEFuIGFycmF5IG9mIHBsdWdpbnNcbiAqL1xuZXhwb3J0IGNvbnN0IHNldHVwTGlicmFyeUV4dGVybmFsID0gKFxuICBpc1Byb2R1Y3Rpb246IGJvb2xlYW4sXG4gIGJhc2VVcmw6IHN0cmluZyxcbiAgZW50cnk6IHN0cmluZ1xuKSA9PiB7XG4gIGNvbnN0IHN0YXRpY1N1ZmZpeCA9IHJhbmRvbXN0cmluZy5nZW5lcmF0ZSh7XG4gICAgbGVuZ3RoOiA4LFxuICAgIGNoYXJzZXQ6IFwiaGV4XCIsXG4gIH0pO1xuXG4gIGNvbnN0IHN0YXRpY1RhcmdldHM6IFRhcmdldFtdID0gW1xuICAgIHtcbiAgICAgIHNyYzogYC4vbm9kZV9tb2R1bGVzL3Z1ZS9kaXN0L3Z1ZS5nbG9iYWwke1xuICAgICAgICBpc1Byb2R1Y3Rpb24gPyBcIi5wcm9kXCIgOiBcIlwiXG4gICAgICB9LmpzYCxcbiAgICAgIGRlc3Q6IFwiYXNzZXRzL3Z1ZVwiLFxuICAgICAgcmVuYW1lOiBgdnVlLmdsb2JhbC4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gICAge1xuICAgICAgc3JjOiBgLi9ub2RlX21vZHVsZXMvdnVlLXJvdXRlci9kaXN0L3Z1ZS1yb3V0ZXIuZ2xvYmFsJHtcbiAgICAgICAgaXNQcm9kdWN0aW9uID8gXCIucHJvZFwiIDogXCJcIlxuICAgICAgfS5qc2AsXG4gICAgICBkZXN0OiBcImFzc2V0cy92dWUtcm91dGVyXCIsXG4gICAgICByZW5hbWU6IGB2dWUtcm91dGVyLmdsb2JhbC4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gICAge1xuICAgICAgc3JjOiBcIi4vbm9kZV9tb2R1bGVzL2F4aW9zL2Rpc3QvYXhpb3MubWluLmpzXCIsXG4gICAgICBkZXN0OiBcImFzc2V0cy9heGlvc1wiLFxuICAgICAgcmVuYW1lOiBgYXhpb3MuJHtzdGF0aWNTdWZmaXh9LmpzYCxcbiAgICB9LFxuICAgIHtcbiAgICAgIHNyYzogYC4vbm9kZV9tb2R1bGVzL3Z1ZS1kZW1pL2xpYi9pbmRleC5paWZlLmpzYCxcbiAgICAgIGRlc3Q6IFwiYXNzZXRzL3Z1ZS1kZW1pXCIsXG4gICAgICByZW5hbWU6IGB2dWUtZGVtaS4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gICAge1xuICAgICAgc3JjOiBcIi4vbm9kZV9tb2R1bGVzL0B2dWV1c2Uvc2hhcmVkL2luZGV4LmlpZmUubWluLmpzXCIsXG4gICAgICBkZXN0OiBcImFzc2V0cy92dWV1c2VcIixcbiAgICAgIHJlbmFtZTogYHZ1ZXVzZS5zaGFyZWQuaWlmZS4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gICAge1xuICAgICAgc3JjOiBcIi4vbm9kZV9tb2R1bGVzL0B2dWV1c2UvY29yZS9pbmRleC5paWZlLm1pbi5qc1wiLFxuICAgICAgZGVzdDogXCJhc3NldHMvdnVldXNlXCIsXG4gICAgICByZW5hbWU6IGB2dWV1c2UuY29yZS5paWZlLiR7c3RhdGljU3VmZml4fS5qc2AsXG4gICAgfSxcbiAgICB7XG4gICAgICBzcmM6IFwiLi9ub2RlX21vZHVsZXMvQHZ1ZXVzZS9jb21wb25lbnRzL2luZGV4LmlpZmUubWluLmpzXCIsXG4gICAgICBkZXN0OiBcImFzc2V0cy92dWV1c2VcIixcbiAgICAgIHJlbmFtZTogYHZ1ZXVzZS5jb21wb25lbnRzLmlpZmUuJHtzdGF0aWNTdWZmaXh9LmpzYCxcbiAgICB9LFxuICAgIHtcbiAgICAgIHNyYzogXCIuL25vZGVfbW9kdWxlcy9AdnVldXNlL3JvdXRlci9pbmRleC5paWZlLm1pbi5qc1wiLFxuICAgICAgZGVzdDogXCJhc3NldHMvdnVldXNlXCIsXG4gICAgICByZW5hbWU6IGB2dWV1c2Uucm91dGVyLmlpZmUuJHtzdGF0aWNTdWZmaXh9LmpzYCxcbiAgICB9LFxuICAgIHtcbiAgICAgIHNyYzogXCIuL25vZGVfbW9kdWxlcy9AaGFsby1kZXYvY29tcG9uZW50cy9kaXN0L2hhbG8tY29tcG9uZW50cy5paWZlLmpzXCIsXG4gICAgICBkZXN0OiBcImFzc2V0cy9jb21wb25lbnRzXCIsXG4gICAgICByZW5hbWU6IGBoYWxvLWNvbXBvbmVudHMuaWlmZS4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gICAge1xuICAgICAgc3JjOiBcIi4vbm9kZV9tb2R1bGVzL0BoYWxvLWRldi9jb25zb2xlLXNoYXJlZC9kaXN0L2hhbG8tY29uc29sZS1zaGFyZWQuaWlmZS5qc1wiLFxuICAgICAgZGVzdDogXCJhc3NldHMvY29uc29sZS1zaGFyZWRcIixcbiAgICAgIHJlbmFtZTogYGhhbG8tY29uc29sZS1zaGFyZWQuaWlmZS4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gICAge1xuICAgICAgc3JjOiBcIi4vbm9kZV9tb2R1bGVzL0BoYWxvLWRldi9yaWNodGV4dC1lZGl0b3IvZGlzdC9yaWNoLXRleHQtZWRpdG9yLmlpZmUuanNcIixcbiAgICAgIGRlc3Q6IFwiYXNzZXRzL3JpY2h0ZXh0LWVkaXRvclwiLFxuICAgICAgcmVuYW1lOiBgaGFsby1yaWNoLXRleHQtZWRpdG9yLmlpZmUuJHtzdGF0aWNTdWZmaXh9LmpzYCxcbiAgICB9LFxuICAgIHtcbiAgICAgIHNyYzogXCIuL25vZGVfbW9kdWxlcy9AaGFsby1kZXYvYXBpLWNsaWVudC9kaXN0L2hhbG8tYXBpLWNsaWVudC5paWZlLmpzXCIsXG4gICAgICBkZXN0OiBcImFzc2V0cy9hcGktY2xpZW50XCIsXG4gICAgICByZW5hbWU6IGBoYWxvLWFwaS1jbGllbnQuaWlmZS4ke3N0YXRpY1N1ZmZpeH0uanNgLFxuICAgIH0sXG4gIF07XG5cbiAgY29uc3QgaW5qZWN0VGFncyA9IHN0YXRpY1RhcmdldHNcbiAgICAubWFwKCh0YXJnZXQpID0+IHtcbiAgICAgIHJldHVybiB7XG4gICAgICAgIGluamVjdFRvOiBcImhlYWRcIixcbiAgICAgICAgdGFnOiBcInNjcmlwdFwiLFxuICAgICAgICBhdHRyczoge1xuICAgICAgICAgIHNyYzogYCR7aXNQcm9kdWN0aW9uID8gYmFzZVVybCA6IFwiL1wifSR7dGFyZ2V0LmRlc3R9LyR7dGFyZ2V0LnJlbmFtZX1gLFxuICAgICAgICAgIHR5cGU6IFwidGV4dC9qYXZhc2NyaXB0XCIsXG4gICAgICAgIH0sXG4gICAgICB9O1xuICAgIH0pXG4gICAgLmZpbHRlcihCb29sZWFuKSBhcyBIdG1sVGFnRGVzY3JpcHRvcltdO1xuXG4gIHJldHVybiBbXG4gICAgVml0ZUV4dGVybmFscyh7XG4gICAgICB2dWU6IFwiVnVlXCIsXG4gICAgICBcInZ1ZS1yb3V0ZXJcIjogXCJWdWVSb3V0ZXJcIixcbiAgICAgIGF4aW9zOiBcImF4aW9zXCIsXG4gICAgICBcIkBoYWxvLWRldi9zaGFyZWRcIjogXCJIYWxvQ29uc29sZVNoYXJlZFwiLFxuICAgICAgXCJAaGFsby1kZXYvY29tcG9uZW50c1wiOiBcIkhhbG9Db21wb25lbnRzXCIsXG4gICAgICBcIkB2dWV1c2UvY29yZVwiOiBcIlZ1ZVVzZVwiLFxuICAgICAgXCJAdnVldXNlL2NvbXBvbmVudHNcIjogXCJWdWVVc2VcIixcbiAgICAgIFwiQHZ1ZXVzZS9yb3V0ZXJcIjogXCJWdWVVc2VcIixcbiAgICAgIFwidnVlLWRlbWlcIjogXCJWdWVEZW1pXCIsXG4gICAgICBcIkBoYWxvLWRldi9yaWNodGV4dC1lZGl0b3JcIjogXCJSaWNoVGV4dEVkaXRvclwiLFxuICAgICAgXCJAaGFsby1kZXYvYXBpLWNsaWVudFwiOiBcIkhhbG9BcGlDbGllbnRcIixcbiAgICB9KSxcbiAgICBWaXRlU3RhdGljQ29weSh7XG4gICAgICB0YXJnZXRzOiBzdGF0aWNUYXJnZXRzLFxuICAgIH0pLFxuICAgIFZpdGVQbHVnaW5IdG1sKHtcbiAgICAgIG1pbmlmeTogZmFsc2UsXG4gICAgICBpbmplY3Q6IHtcbiAgICAgICAgdGFnczogaW5qZWN0VGFncyxcbiAgICAgICAgZGF0YToge1xuICAgICAgICAgIGVudHJ5OiBlbnRyeSxcbiAgICAgICAgfSxcbiAgICAgIH0sXG4gICAgfSksXG4gIF07XG59O1xuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUFnUSxPQUFPLG1CQUFtQjtBQUMxUixPQUFPQSxXQUFVOzs7QUNEZ1IsT0FBTyxTQUFTO0FBQ2pULE9BQU8sWUFBWTtBQUNuQixPQUFPLFFBQVE7QUFDZixPQUFPLFVBQVU7QUFDakIsT0FBTyxnQkFBZ0I7QUFDdkIsT0FBTyxXQUFXO0FBQ2xCLFNBQVMscUJBQXFCO0FBQzlCLFNBQVMsb0JBQWlDO0FBQzFDLFNBQVMsZUFBZTtBQUN4QixPQUFPLGlCQUFpQjs7O0FDVDZRLE9BQU8sa0JBQWtCO0FBRTlULFNBQVMsdUJBQXVCLHFCQUFxQjtBQUNyRCxTQUFTLG9CQUFvQixzQkFBc0I7QUFDbkQ7QUFBQSxFQUNFLGtCQUFrQjtBQUFBLE9BRWI7QUFTQSxJQUFNLHVCQUF1QixDQUNsQyxjQUNBLFNBQ0EsVUFDRztBQUNILFFBQU0sZUFBZSxhQUFhLFNBQVM7QUFBQSxJQUN6QyxRQUFRO0FBQUEsSUFDUixTQUFTO0FBQUEsRUFDWCxDQUFDO0FBRUQsUUFBTSxnQkFBMEI7QUFBQSxJQUM5QjtBQUFBLE1BQ0UsS0FBSyxxQ0FDSCxlQUFlLFVBQVUsRUFDM0I7QUFBQSxNQUNBLE1BQU07QUFBQSxNQUNOLFFBQVEsY0FBYyxZQUFZO0FBQUEsSUFDcEM7QUFBQSxJQUNBO0FBQUEsTUFDRSxLQUFLLG1EQUNILGVBQWUsVUFBVSxFQUMzQjtBQUFBLE1BQ0EsTUFBTTtBQUFBLE1BQ04sUUFBUSxxQkFBcUIsWUFBWTtBQUFBLElBQzNDO0FBQUEsSUFDQTtBQUFBLE1BQ0UsS0FBSztBQUFBLE1BQ0wsTUFBTTtBQUFBLE1BQ04sUUFB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QUFTLGlCQUFpQixTQUFrQjtBQUNqRCxRQUFNLGVBQWUsUUFBUSxTQUFTO0FBRXRDLFFBQU0sRUFBRSxNQUFNLFdBQVcsTUFBTSxRQUFRLFFBQVEsSUFBSTtBQUVuRCxRQUFNLGlCQUFpQixLQUFLLFFBQVEsY0FBYyx3Q0FBZSxDQUFDO0FBQ2xFLFFBQU0sVUFBVSxLQUFLLFFBQVEsZ0JBQWdCLE9BQU87QUFFcEQsU0FBTyxhQUFhO0FBQUEsSUFDbEI7QUFBQSxJQUNBLFNBQVM7QUFBQSxNQUNQLEdBQUc7QUFBQSxNQUNILEdBQUcscUJBQXFCLGNBQWMsTUFBTSxTQUFTO0FBQUEsTUFDckQsR0FBSSxXQUFXLENBQUM7QUFBQSxJQUNsQjtBQUFBLElBQ0EsU0FBUztBQUFBLE1BQ1AsT0FBTztBQUFBLFFBQ0wsS0FBSyxLQUFLLFFBQVEsU0FBUyxLQUFLO0FBQUEsUUFDaEMsWUFBWSxLQUFLLFFBQVEsU0FBUyxhQUFhO0FBQUEsUUFDL0MsT0FBTyxLQUFLLFFBQVEsU0FBUyxRQUFRO0FBQUEsTUFDdkM7QUFBQSxJQUNGO0FBQUEsSUFDQSxRQUFRO0FBQUEsTUFDTjtBQUFBLE1BQ0EsSUFBSTtBQUFBLFFBQ0YsUUFBUSxlQUFlLE9BQU87QUFBQSxNQUNoQztBQUFBLElBQ0Y7QUFBQSxJQUNBLE9BQU87QUFBQSxNQUNMLFFBQVEsS0FBSyxRQUFRLFNBQVMsTUFBTTtBQUFBLE1BQ3BDLGFBQWE7QUFBQSxNQUNiLHVCQUF1QjtBQUFBLE1BQ3ZCLGVBQWU7QUFBQSxRQUNiLFFBQVE7QUFBQSxVQUNOLGNBQWM7QUFBQSxZQUNaLFFBQVE7QUFBQSxjQUNOO0FBQUEsY0FDQTtBQUFBLGNBQ0E7QUFBQSxjQUNBO0FBQUEsY0FDQTtBQUFBLGNBQ0E7QUFBQSxjQUNBO0FBQUEsY0FDQTtBQUFBLGNBQ0E7QUFBQSxjQUNBO0FBQUEsWUFDRjtBQUFBLFVBQ0Y7QUFBQSxRQUNGO0FBQUEsTUFDRjtBQUFBLElBQ0Y7QUFBQSxFQUNGLENBQUM7QUFDSDs7O0FEckdBLElBQU0sbUNBQW1DO0FBS3pDLElBQU8sc0JBQVEsQ0FBQyxFQUFFLEtBQUssTUFBd0I7QUFDN0MsU0FBTyxpQkFBaUI7QUFBQSxJQUN0QixNQUFNO0FBQUEsSUFDTixXQUFXO0FBQUEsSUFDWCxNQUFNO0FBQUEsSUFDTixRQUFRQyxNQUFLLFFBQVEsb0JBQW9CO0FBQUEsSUFDekM7QUFBQSxJQUNBLFNBQVM7QUFBQSxNQUNQLGNBQWM7QUFBQSxRQUNaLFNBQVMsQ0FBQ0EsTUFBSyxRQUFRLGtDQUFXLHNCQUFzQixDQUFDO0FBQUEsTUFDM0QsQ0FBQztBQUFBLElBQ0g7QUFBQSxFQUNGLENBQUM7QUFDSDsiLAogICJuYW1lcyI6IFsicGF0aCIsICJwYXRoIl0KfQo=
