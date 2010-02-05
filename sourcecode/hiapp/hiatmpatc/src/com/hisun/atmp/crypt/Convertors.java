package com.hisun.atmp.crypt;

public class Convertors {
    public static final char[] mask = {'1', '9', 'F', '2', 'C', '8', '2', '7', '6', 'A', 'D', '0', '8', '3', '9', 'B'};

    public static final char[] step = {'3', '5', '8', '2', '7', '6', '2', '9'};
    public static final char[] keymask = {'B', 'A', '9', '8', '7', 'D', '4', '0'};
    public static final int TRANKEY_LEN = 8;

    static Date charDate(char[] data) {
        return new charDate(data);
    }

    static Convertor map2(Date m, Map2 map) {
        return new Convertor(map, m) {
            private final Convertors.Map2 val$map;
            private final Convertors.Date val$m;

            public Convertors.Date convert(Convertors.Date v) {
                return new Convertors.Date(v) {
                    private final Convertors.Date val$v;

                    public int get(int i) {
                        return Convertors
                        .1.
                        this.val$map.c(this.val$v.get(i), Convertors
                        .1.
                        this.val$m.get(i));
                    }

                    public int len() {
                        return this.val$v.len();
                    }
                };
            }
        };
    }

    static Convertor xor(Date m) {
        return map2(m, Maps.mask());
    }

    static Convertor leftpad(int c, int len) {
        return new Convertor(len, c) {
            private final int val$len;
            private final int val$c;

            public Convertors.Date convert(Convertors.Date v) {
                return v.leftpad(this.val$len, this.val$c);
            }
        };
    }

    static Convertor rightpad(int c, int len) {
        return new Convertor(len, c) {
            private final int val$len;
            private final int val$c;

            public Convertors.Date convert(Convertors.Date v) {
                return v.pad(0, this.val$len, this.val$c);
            }
        };
    }

    static Convertor leftpad() {
        return leftpad(0, 16);
    }

    static Convertor right(int len) {
        return new Convertor(len) {
            private final int val$len;

            public Convertors.Date convert(Convertors.Date v) {
                return new Convertors.Date(v) {
                    private int pos = this.val$v.len() - Convertors
                    .4.this.val$len;
                    private final Convertors.Date val$v;

                    public int get(int i) {
                        return this.val$v.get(i + this.pos);
                    }

                    public int len() {
                        return Convertors
                        .4.
                        this.val$len;
                    }
                };
            }
        };
    }

    static Convertor pack() {
        return new Convertor() {
            public Convertors.Date convert(Convertors.Date v) {
                return new Convertors.Date(v) {
                    private final Convertors.Date val$v;

                    public int get(int i) {
                        return (this.val$v.get(2 * i) * 16 + this.val$v.get(2 * i + 1));
                    }

                    public int len() {
                        return (this.val$v.len() / 2);
                    }
                };
            }
        };
    }

    static Convertor unpack() {
        return new Convertor() {
            public Convertors.Date convert(Convertors.Date v) {
                return new Convertors.Date(v) {
                    private final Convertors.Date val$v;

                    public int get(int i) {
                        int n = this.val$v.get(i / 2);
                        if (i % 2 == 0) {
                            return (n / 16);
                        }
                        return (n % 16);
                    }

                    public int len() {
                        return (this.val$v.len() * 2);
                    }
                };
            }
        };
    }

    static Convertor incadd(Date key) {
        return new Convertor(key) {
            private final Convertors.Date val$key;

            public Convertors.Date convert(Convertors.Date v) {
                Convertors.charDate data = new Convertors.charDate(new char[v.len()]);

                int a = 0;
                for (int i = v.len() - 1; i >= 0; --i) {
                    int n = v.get(i) + this.val$key.get(i) + a;
                    data.set(i, n % 10);
                    a = n / 10;
                }
                return data;
            }
        };
    }

    static Convertor incsub(Date key) {
        return new Convertor(key) {
            private final Convertors.Date val$key;

            public Convertors.Date convert(Convertors.Date v) {
                Convertors.charDate data = new Convertors.charDate(new char[v.len()]);

                int a = 0;
                for (int i = v.len() - 1; i >= 0; --i) {
                    int n = v.get(i) - this.val$key.get(i) - a;
                    if (n < 0) {
                        n += 10;
                        a = 1;
                    } else {
                        a = 0;
                    }
                    data.set(i, n % 10);
                }
                return data;
            }
        };
    }

    static Convertor bind(Convertor c1, Convertor c2) {
        return new Convertor(c2, c1) {
            private final Convertor val$c2;
            private final Convertor val$c1;

            public Convertors.Date convert(Convertors.Date v) {
                return this.val$c2.convert(this.val$c1.convert(v));
            }
        };
    }

    static Convertor pinblockkey(Date key) {
        return new Convertor(key) {
            private final Convertors.Date val$key;

            public Convertors.Date convert(Convertors.Date v) {
                return new Convertors.Date(v) {
                    private final Convertors.Date val$v;

                    public int get(int i) {
                        int n = this.val$v.get(i);
                        if (n > 9) n = 0;
                        return Convertors
                        .10.
                        this.val$key.get(n);
                    }

                    public int len() {
                        return this.val$v.len();
                    }
                };
            }
        };
    }

    static Convertor pinblockkey() {
        return rightpad(0, 8).pinblockkey(charDate("1122447788".toCharArray()));
    }

    static Convertor des(char[] encryptKey, int flag) {
        return null;
    }

    static Convertor pinblock(char[] trm_key) {
        char[] k = pinblockkey().convert(trm_key);
        return pack().des(k, 1).unpack();
    }

    static Convertor shuffle(char[] trm_key, char[] mask) {
        return leftpad(0, 16).incadd(charDate(trm_key).leftpad(16, 0)).xor(charDate(mask));
    }

    static Convertor unshuffle(char[] trm_key, char[] mask) {
        return leftpad(0, 16).xor(charDate(mask)).incsub(charDate(trm_key).leftpad(16, 0));
    }

    public static char[] shuffle2(char[] in, char[] trm_key, char[] mask) {
        return shuffle(trm_key, mask).right(in.length).convert(in);
    }

    public static char[] unshuffle2(char[] in, char[] trm_key, char[] mask) {
        return unshuffle(trm_key, mask).right(in.length).convert(in);
    }

    static class Maps {
        private static Convertors.Map2 _mask = new Convertors.Map2() {
            public int c(int v1, int v2) {
                return (v1 ^ v2);
            }
        };

        static Convertors.Map2 mask() {
            return _mask;
        }
    }

    static abstract interface Map2 {
        public abstract int c(int paramInt1, int paramInt2);
    }

    static class charDate extends Convertors.Date {
        final char[] data;

        public charDate(char[] d) {
            this.data = d;
        }

        public charDate(String s) {
            this.data = s.toCharArray();
        }

        public int get(int i) {
            return toint(this.data[i]);
        }

        public int len() {
            return this.data.length;
        }

        public void set(int i, int v) {
            this.data[i] = tochar(v);
        }
    }

    static abstract class Date {
        public abstract int len();

        public abstract int get(int paramInt);

        public char[] toChars() {
            char[] data = new char[len()];
            for (int i = 0; i < data.length; ++i)
                data[i] = tochar(get(i));
            return data;
        }

        public int toint(char c) {
            return ((c >= 'A') ? c - 'A' + 10 : c - '0');
        }

        public char tochar(int v) {
            return (char) ((v < 10) ? v + 48 : v % 10 + 65);
        }

        public Date reverse() {
            Date data = this;
            return new Date(data) {
                private final Convertors.Date val$data;

                public int get(int i) {
                    return this.val$data.get(this.val$data.len() - i - 1);
                }

                public int len() {
                    return this.val$data.len();
                }
            };
        }

        private Date pad(int pos, int length, int c) {
            Date data = this;
            return new Date(pos, data, c, length) {
                private final int val$pos;
                private final Convertors.Date val$data;
                private final int val$c;
                private final int val$length;

                public int get(int i) {
                    if ((i >= this.val$pos) && (i < this.val$pos + this.val$data.len()))
                        return this.val$data.get(i - this.val$pos);
                    return this.val$c;
                }

                public int len() {
                    return this.val$length;
                }
            };
        }

        public Date leftpad(int len, int c) {
            return pad(len - len(), len, c);
        }

        public Date rightpad(int len, int c) {
            return pad(0, len, c);
        }

        public Date append(Date d) {
            Date d1 = this;
            return new Date(d1, d) {
                final int len = this.val$d1.len() + this.val$d.len();
                private final Convertors.Date val$d1;
                private final Convertors.Date val$d;

                public int get(int i) {
                    if (i < this.val$d1.len()) return this.val$d1.get(i);
                    return this.val$d.get(i - this.val$d1.len());
                }

                public int len() {
                    return this.len;
                }
            };
        }
    }
}