export const overrideByKeys = (
  base: Record<string, any>,
  override: Record<string, any>,
) => {
  return Object.fromEntries(
    Object.keys(base).map((key) => [
      key,
      key in override ? override[key] : base[key],
    ]),
  );
};
