//ʱ��
function time() {
    return Math.floor((new Date).getTime() / 1e3)
}

function date(n, t) {
    var e, r,
        u = ["Sun", "Mon", "Tues", "Wednes", "Thurs", "Fri", "Satur", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
        o = /\\?(.?)/gi, i = function (n, t) {
            return r[n] ? r[n]() : t
        }, c = function (n, t) {
            for (n = String(n); n.length < t;) n = "0" + n;
            return n
        };
    r = {
        d: function () {
            return c(r.j(), 2)
        }, D: function () {
            return r.l().slice(0, 3)
        }, j: function () {
            return e.getDate()
        }, l: function () {
            return u[r.w()] + "day"
        }, N: function () {
            return r.w() || 7
        }, S: function () {
            var n = r.j(), t = n % 10;
            return t <= 3 && 1 === parseInt(n % 100 / 10, 10) && (t = 0), ["st", "nd", "rd"][t - 1] || "th"
        }, w: function () {
            return e.getDay()
        }, z: function () {
            var n = new Date(r.Y(), r.n() - 1, r.j()), t = new Date(r.Y(), 0, 1);
            return Math.round((n - t) / 864e5)
        }, W: function () {
            var n = new Date(r.Y(), r.n() - 1, r.j() - r.N() + 3), t = new Date(n.getFullYear(), 0, 4);
            return c(1 + Math.round((n - t) / 864e5 / 7), 2)
        }, F: function () {
            return u[6 + r.n()]
        }, m: function () {
            return c(r.n(), 2)
        }, M: function () {
            return r.F().slice(0, 3)
        }, n: function () {
            return e.getMonth() + 1
        }, t: function () {
            return new Date(r.Y(), r.n(), 0).getDate()
        }, L: function () {
            var n = r.Y();
            return n % 4 == 0 & n % 100 != 0 | n % 400 == 0
        }, o: function () {
            var n = r.n(), t = r.W();
            return r.Y() + (12 === n && t < 9 ? 1 : 1 === n && t > 9 ? -1 : 0)
        }, Y: function () {
            return e.getFullYear()
        }, y: function () {
            return r.Y().toString().slice(-2)
        }, a: function () {
            return e.getHours() > 11 ? "pm" : "am"
        }, A: function () {
            return r.a().toUpperCase()
        }, B: function () {
            var n = 3600 * e.getUTCHours(), t = 60 * e.getUTCMinutes(), r = e.getUTCSeconds();
            return c(Math.floor((n + t + r + 3600) / 86.4) % 1e3, 3)
        }, g: function () {
            return r.G() % 12 || 12
        }, G: function () {
            return e.getHours()
        }, h: function () {
            return c(r.g(), 2)
        }, H: function () {
            return c(r.G(), 2)
        }, i: function () {
            return c(e.getMinutes(), 2)
        }, s: function () {
            return c(e.getSeconds(), 2)
        }, u: function () {
            return c(1e3 * e.getMilliseconds(), 6)
        }, e: function () {
            throw new Error("Not supported (see source code of date() for timezone on how to add support)")
        }, I: function () {
            return new Date(r.Y(), 0) - Date.UTC(r.Y(), 0) != new Date(r.Y(), 6) - Date.UTC(r.Y(), 6) ? 1 : 0
        }, O: function () {
            var n = e.getTimezoneOffset(), t = Math.abs(n);
            return (n > 0 ? "-" : "+") + c(100 * Math.floor(t / 60) + t % 60, 4)
        }, P: function () {
            var n = r.O();
            return n.substr(0, 3) + ":" + n.substr(3, 2)
        }, T: function () {
            return "UTC"
        }, Z: function () {
            return 60 * -e.getTimezoneOffset()
        }, c: function () {
            return "Y-m-d\\TH:i:sP".replace(o, i)
        }, r: function () {
            return "D, d M Y H:i:s O".replace(o, i)
        }, U: function () {
            return e / 1e3 | 0
        }
    };
    return function (n, t) {
        return e = void 0 === t ? new Date : t instanceof Date ? new Date(t) : new Date(1e3 * t), n.replace(o, i)
    }(n, t)
}

function strtotime(text, now) {
    var parsed, match, today, year, date, days, ranges, len, times, regex, i, fail = false;
    if (!text) {
        return fail
    }
    text = text.replace(/^\s+|\s+$/g, "").replace(/\s{2,}/g, " ").replace(/[\t\r\n]/g, "").toLowerCase();
    match = text.match(/^(\d{1,4})([\-\.\/\:])(\d{1,2})([\-\.\/\:])(\d{1,4})(?:\s(\d{1,2}):(\d{2})?:?(\d{2})?)?(?:\s([A-Z]+)?)?$/);
    if (match && match[2] === match[4]) {
        if (match[1] > 1901) {
            switch (match[2]) {
                case"-":
                    if (match[3] > 12 || match[5] > 31) {
                        return fail
                    }
                    return new Date(match[1], parseInt(match[3], 10) - 1, match[5], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000;
                case".":
                    return fail;
                case"/":
                    if (match[3] > 12 || match[5] > 31) {
                        return fail
                    }
                    return new Date(match[1], parseInt(match[3], 10) - 1, match[5], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000
            }
        } else {
            if (match[5] > 1901) {
                switch (match[2]) {
                    case"-":
                        if (match[3] > 12 || match[1] > 31) {
                            return fail
                        }
                        return new Date(match[5], parseInt(match[3], 10) - 1, match[1], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000;
                    case".":
                        if (match[3] > 12 || match[1] > 31) {
                            return fail
                        }
                        return new Date(match[5], parseInt(match[3], 10) - 1, match[1], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000;
                    case"/":
                        if (match[1] > 12 || match[3] > 31) {
                            return fail
                        }
                        return new Date(match[5], parseInt(match[1], 10) - 1, match[3], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000
                }
            } else {
                switch (match[2]) {
                    case"-":
                        if (match[3] > 12 || match[5] > 31 || (match[1] < 70 && match[1] > 38)) {
                            return fail
                        }
                        year = match[1] >= 0 && match[1] <= 38 ? +match[1] + 2000 : match[1];
                        return new Date(year, parseInt(match[3], 10) - 1, match[5], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000;
                    case".":
                        if (match[5] >= 70) {
                            if (match[3] > 12 || match[1] > 31) {
                                return fail
                            }
                            return new Date(match[5], parseInt(match[3], 10) - 1, match[1], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000
                        }
                        if (match[5] < 60 && !match[6]) {
                            if (match[1] > 23 || match[3] > 59) {
                                return fail
                            }
                            today = new Date();
                            return new Date(today.getFullYear(), today.getMonth(), today.getDate(), match[1] || 0, match[3] || 0, match[5] || 0, match[9] || 0) / 1000
                        }
                        return fail;
                    case"/":
                        if (match[1] > 12 || match[3] > 31 || (match[5] < 70 && match[5] > 38)) {
                            return fail
                        }
                        year = match[5] >= 0 && match[5] <= 38 ? +match[5] + 2000 : match[5];
                        return new Date(year, parseInt(match[1], 10) - 1, match[3], match[6] || 0, match[7] || 0, match[8] || 0, match[9] || 0) / 1000;
                    case":":
                        if (match[1] > 23 || match[3] > 59 || match[5] > 59) {
                            return fail
                        }
                        today = new Date();
                        return new Date(today.getFullYear(), today.getMonth(), today.getDate(), match[1] || 0, match[3] || 0, match[5] || 0) / 1000
                }
            }
        }
    }
    if (text === "now") {
        return now === null || isNaN(now) ? new Date().getTime() / 1000 | 0 : now | 0
    }
    if (!isNaN(parsed = Date.parse(text))) {
        return parsed / 1000 | 0
    }
    date = now ? new Date(now * 1000) : new Date();
    days = {"sun": 0, "mon": 1, "tue": 2, "wed": 3, "thu": 4, "fri": 5, "sat": 6};
    ranges = {"yea": "FullYear", "mon": "Month", "day": "Date", "hou": "Hours", "min": "Minutes", "sec": "Seconds"};

    function lastNext(type, range, modifier) {
        var diff, day = days[range];
        if (typeof day !== "undefined") {
            diff = day - date.getDay();
            if (diff === 0) {
                diff = 7 * modifier
            } else {
                if (diff > 0 && type === "last") {
                    diff -= 7
                } else {
                    if (diff < 0 && type === "next") {
                        diff += 7
                    }
                }
            }
            date.setDate(date.getDate() + diff)
        }
    }

    function process(val) {
        var splt = val.split(" "), type = splt[0], range = splt[1].substring(0, 3), typeIsNumber = /\d+/.test(type),
            ago = splt[2] === "ago", num = (type === "last" ? -1 : 1) * (ago ? -1 : 1);
        if (typeIsNumber) {
            num *= parseInt(type, 10)
        }
        if (ranges.hasOwnProperty(range) && !splt[1].match(/^mon(day|\.)?$/i)) {
            return date["set" + ranges[range]](date["get" + ranges[range]]() + num)
        }
        if (range === "wee") {
            return date.setDate(date.getDate() + (num * 7))
        }
        if (type === "next" || type === "last") {
            lastNext(type, range, num)
        } else {
            if (!typeIsNumber) {
                return false
            }
        }
        return true
    }

    times = "(years?|months?|weeks?|days?|hours?|minutes?|min|seconds?|sec" + "|sunday|sun\\.?|monday|mon\\.?|tuesday|tue\\.?|wednesday|wed\\.?" + "|thursday|thu\\.?|friday|fri\\.?|saturday|sat\\.?)";
    regex = "([+-]?\\d+\\s" + times + "|" + "(last|next)\\s" + times + ")(\\sago)?";
    match = text.match(new RegExp(regex, "gi"));
    if (!match) {
        return fail
    }
    for (i = 0, len = match.length; i < len; i++) {
        if (!process(match[i])) {
            return fail
        }
    }
    return (date.getTime() / 1000)
};

function microtime(e) {
    var n, r;
    return "undefined" != typeof performance && performance.now ? (r = (performance.now() + performance.timing.navigationStart) / 1e3, e ? r : (n = 0 | r, Math.round(1e6 * (r - n)) / 1e6 + " " + n)) : (r = (Date.now ? Date.now() : (new Date).getTime()) / 1e3, e ? r : (n = 0 | r, Math.round(1e3 * (r - n)) / 1e3 + " " + n))
}

function mktime() {
    var e = new Date, t = arguments, r = 0, n = ["Hours", "Minutes", "Seconds", "Month", "Date", "FullYear"];
    for (r = 0; r < n.length; r++) if (void 0 === t[r]) t[r] = e["get" + n[r]](), t[r] += 3 === r; else if (t[r] = parseInt(t[r], 10), isNaN(t[r])) return !1;
    t[5] += t[5] >= 0 ? t[5] <= 69 ? 2e3 : t[5] <= 100 ? 1900 : 0 : 0, e.setFullYear(t[5], t[3] - 1, t[4]), e.setHours(t[0], t[1], t[2]);
    var s = e.getTime();
    return (s / 1e3 >> 0) - (s < 0)
}

//����
function array_column(r, t) {
    var o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : null;
    if (null !== r && ("object" === (void 0 === r ? "undefined" : _typeof(r)) || Array.isArray(r))) {
        var e = [];
        if ("object" === (void 0 === r ? "undefined" : _typeof(r))) {
            var n = [], y = !0, i = !1, a = void 0;
            try {
                for (var f, l = Object.keys(r)[Symbol.iterator](); !(y = (f = l.next()).done); y = !0) {
                    var u = f.value;
                    n.push(r[u])
                }
            } catch (r) {
                i = !0, a = r
            } finally {
                try {
                    !y && l.return && l.return()
                } finally {
                    if (i) throw a
                }
            }
            r = n
        }
        if (Array.isArray(r)) {
            var c = !0, v = !1, s = void 0;
            try {
                for (var b, p = r.keys()[Symbol.iterator](); !(c = (b = p.next()).done); c = !0) {
                    var d = b.value;
                    o && r[d][o] ? e[r[d][o]] = t ? r[d][t] : r[d] : t ? e.push(r[d][t]) : e.push(r[d])
                }
            } catch (r) {
                v = !0, s = r
            } finally {
                try {
                    !c && p.return && p.return()
                } finally {
                    if (v) throw s
                }
            }
        }
        return Object.assign({}, e)
    }
}

var _typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (r) {
    return typeof r
} : function (r) {
    return r && "function" == typeof Symbol && r.constructor === Symbol && r !== Symbol.prototype ? "symbol" : typeof r
};

function array_keys(r, n, a) {
    var e = void 0 !== n, o = [], t = !!a, i = !0, y = "";
    for (y in r) r.hasOwnProperty(y) && (i = !0, e && (t && r[y] !== n ? i = !1 : r[y] !== n && (i = !1)), i && (o[o.length] = y));
    return o
}

function array_values(r) {
    var n = [], a = "";
    for (a in r) n[n.length] = r[a];
    return n
}

function array_slice(r, i, e, t) {
    var o = "";
    if ("[object Array]" !== Object.prototype.toString.call(r) || t && 0 !== i) {
        var a = 0, c = {};
        for (o in r) a += 1, c[o] = r[o];
        r = c, i = i < 0 ? a + i : i, e = void 0 === e ? a : e < 0 ? a + e - i : e;
        var n = {}, l = !1, f = -1, s = 0, v = 0;
        for (o in r) {
            if (++f, s >= e) break;
            f === i && (l = !0), l && (++s, is_int(o) && !t ? n[v++] = r[o] : n[o] = r[o])
        }
        return n
    }
    return void 0 === e ? r.slice(i) : e >= 0 ? r.slice(i, i + e) : r.slice(i, e)
}

function array_search(r, e, t) {
    var n = !!t, i = "";
    if ("object" == typeof r && r.exec) {
        if (!n) {
            var o = "i" + (r.global ? "g" : "") + (r.multiline ? "m" : "") + (r.sticky ? "y" : "");
            r = new RegExp(r.source, o)
        }
        for (i in e) if (e.hasOwnProperty(i) && r.test(e[i])) return i;
        return !1
    }
    for (i in e) if (e.hasOwnProperty(i) && (n && e[i] === r || !n && e[i] == r)) return i;
    return !1
}

function array_merge() {
    var r, e = Array.prototype.slice.call(arguments), t = e.length, o = {}, a = "", n = 0, c = 0, l = 0, f = 0,
        i = Object.prototype.toString, y = !0;
    for (l = 0; l < t; l++) if ("[object Array]" !== i.call(e[l])) {
        y = !1;
        break
    }
    if (y) {
        for (y = [], l = 0; l < t; l++) y = y.concat(e[l]);
        return y
    }
    for (l = 0, f = 0; l < t; l++) if (r = e[l], "[object Array]" === i.call(r)) for (c = 0, n = r.length; c < n; c++) o[f++] = r[c]; else for (a in r) r.hasOwnProperty(a) && (parseInt(a, 10) + "" === a ? o[f++] = r[a] : o[a] = r[a]);
    return o
}

function count(r, t) {
    var n, o = 0;
    if (null === r || void 0 === r) return 0;
    if (r.constructor !== Array && r.constructor !== Object) return 1;
    "COUNT_RECURSIVE" === t && (t = 1), 1 !== t && (t = 0);
    for (n in r) r.hasOwnProperty(n) && (o++, 1 !== t || !r[n] || r[n].constructor !== Array && r[n].constructor !== Object || (o += count(r[n], 1)));
    return o
}

function in_array(r, n, i) {
    var f = "";
    if (!i) {
        for (f in n) if (n[f] == r) return !0
    } else for (f in n) if (n[f] === r) return !0;
    return !1
}

function range(r, a, N) {
    var i, o, s = [], e = N || 1, f = !1;
    if (isNaN(r) || isNaN(a) ? isNaN(r) && isNaN(a) ? (f = !0, i = r.charCodeAt(0), o = a.charCodeAt(0)) : (i = isNaN(r) ? 0 : r, o = isNaN(a) ? 0 : a) : (i = r, o = a), !(i > o)) for (; i <= o;) s.push(f ? String.fromCharCode(i) : i), i += e; else for (; i >= o;) s.push(f ? String.fromCharCode(i) : i), i -= e;
    return s
}

//����
function strcmp(b, a) {
    return ((b == a) ? 0 : ((b > a) ? 1 : -1))
};

function strnatcmp(g, f, d) {
    var b = 0;
    if (d == undefined) {
        d = false
    }
    var e = function (p) {
        var n = [];
        var o = "";
        var r = "";
        var q = 0, m = 0;
        var s = true;
        m = p.length;
        for (q = 0; q < m; q++) {
            r = p.substring(q, q + 1);
            if (r.match(/\d/)) {
                if (s) {
                    if (o.length > 0) {
                        n[n.length] = o;
                        o = ""
                    }
                    s = false
                }
                o += r
            } else {
                if ((s == false) && (r === ".") && (q < (p.length - 1)) && (p.substring(q + 1, q + 2).match(/\d/))) {
                    n[n.length] = o;
                    o = ""
                } else {
                    if (s == false) {
                        if (o.length > 0) {
                            n[n.length] = parseInt(o, 10);
                            o = ""
                        }
                        s = true
                    }
                    o += r
                }
            }
        }
        if (o.length > 0) {
            if (s) {
                n[n.length] = o
            } else {
                n[n.length] = parseInt(o, 10)
            }
        }
        return n
    };
    var l = e(g + "");
    var j = e(f + "");
    var c = l.length;
    var h = true;
    var k = -1;
    var a = 0;
    if (c > j.length) {
        c = j.length;
        k = 1
    }
    for (b = 0; b < c; b++) {
        if (isNaN(l[b])) {
            if (isNaN(j[b])) {
                h = true;
                if ((a = strcmp(l[b], j[b])) != 0) {
                    return a
                }
            } else {
                if (h) {
                    return 1
                } else {
                    return -1
                }
            }
        } else {
            if (isNaN(j[b])) {
                if (h) {
                    return -1
                } else {
                    return 1
                }
            } else {
                if (h || d) {
                    if ((a = (l[b] - j[b])) != 0) {
                        return a
                    }
                } else {
                    if ((a = strcmp(l[b].toString(), j[b].toString())) != 0) {
                        return a
                    }
                }
                h = false
            }
        }
    }
    return k
};

function sort(inputArr, sort_flags, strictForIn = true) {
    var valArr = [], keyArr = [], k = '', i = 0, sorter = false, populateArr = [];
    switch (sort_flags) {
        case'SORT_STRING':
            sorter = function (a, b) {
                return strnatcmp(a, b);
            };
            break;
        case'SORT_NUMERIC':
            sorter = function (a, b) {
                return (a - b);
            };
            break;
        default:
            sorter = function (a, b) {
                var aFloat = parseFloat(a), bFloat = parseFloat(b), aNumeric = aFloat + '' === a,
                    bNumeric = bFloat + '' === b;
                if (aNumeric && bNumeric) {
                    return aFloat > bFloat ? 1 : aFloat < bFloat ? -1 : 0;
                } else if (aNumeric && !bNumeric) {
                    return 1;
                } else if (!aNumeric && bNumeric) {
                    return -1;
                }
                return a > b ? 1 : a < b ? -1 : 0;
            };
            break;
    }
    populateArr = strictForIn ? inputArr : populateArr;
    for (k in inputArr) {
        if (inputArr.hasOwnProperty(k)) {
            valArr.push(inputArr[k]);
            if (strictForIn) {
                delete inputArr[k];
            }
        }
    }
    valArr.sort(sorter);
    for (i = 0; i < valArr.length; i++) {
        populateArr[i] = valArr[i];
    }
    return strictForIn || populateArr;
}

function ksort(inputArr, sort_flags) {
    return sort(inputArr, sort_flags, false);
};

//��������
function is_int(i) {
    return i === +i && isFinite(i) && !(i % 1)
}

function is_float(i) {
    return !(+i !== i || isFinite(i) && !(i % 1))
}

function is_array(t) {
    if (!t || "object" != typeof t) return !1;
    if (function (t) {
        if (!t || "object" != typeof t || "number" != typeof t.length) return !1;
        var e = t.length;
        return t[t.length] = "bogus", e !== t.length ? (t.length -= 1, !0) : (delete t[t.length], !1)
    }(t)) return !0;
    var e = Object.prototype.toString.call(t), n = function (t) {
        var e = /\W*function\s+([\w$]+)\s*\(/.exec(t);
        return e ? e[1] : "(Anonymous)"
    }(t.constructor);
    return "[object Object]" === e && "Object" === n
}

function is_object(t) {
    return "[object Array]" !== Object.prototype.toString.call(t) && (null !== t && "object" == typeof t)
}

function uniqid(n, e) {
    void 0 === n && (n = "");
    var t, i = function (n, e) {
        return n = parseInt(n, 10).toString(16), e < n.length ? n.slice(n.length - e) : e > n.length ? Array(e - n.length + 1).join("0") + n : n
    }, o = "undefined" != typeof window ? window : global;
    o.$locutus = o.$locutus || {};
    var d = o.$locutus;
    return d.php = d.php || {}, d.php.uniqidSeed || (d.php.uniqidSeed = Math.floor(123456789 * Math.random())), d.php.uniqidSeed++, t = n, t += i(parseInt((new Date).getTime() / 1e3, 10), 8), t += i(d.php.uniqidSeed, 5), e && (t += (10 * Math.random()).toFixed(8).toString()), t
}

function empty(r) {
    var n, t, e, f = [void 0, null, !1, 0, "", "0"];
    for (t = 0, e = f.length; t < e; t++) if (r === f[t]) return !0;
    if ("object" == typeof r) {
        for (n in r) if (r.hasOwnProperty(n)) return !1;
        return !0
    }
    return !1
}

function isset() {
    var r = arguments, t = r.length, n = 0;
    if (0 === t) throw new Error("Empty isset");
    for (; n !== t;) {
        if (void 0 === r[n] || null === r[n]) return !1;
        n++
    }
    return !0
}

function intval(i, t) {
    var n, a, e = typeof i;
    return "boolean" === e ? +i : "string" === e ? (0 === t && (a = i.match(/^\s*0(x?)/i), t = a ? a[1] ? 16 : 8 : 10), n = parseInt(i, t || 10), isNaN(n) || !isFinite(n) ? 0 : n) : "number" === e && isFinite(i) ? i < 0 ? Math.ceil(i) : Math.floor(i) : 0
}

function floatval(a) {
    return parseFloat(a) || 0
}

//����
function md5(C) {
    var D;
    var w = function (b, a) {
        return (b << a) | (b >>> (32 - a))
    };
    var H = function (k, b) {
        var V, a, d, x, c;
        d = (k & 2147483648);
        x = (b & 2147483648);
        V = (k & 1073741824);
        a = (b & 1073741824);
        c = (k & 1073741823) + (b & 1073741823);
        if (V & a) {
            return (c ^ 2147483648 ^ d ^ x)
        }
        if (V | a) {
            if (c & 1073741824) {
                return (c ^ 3221225472 ^ d ^ x)
            } else {
                return (c ^ 1073741824 ^ d ^ x)
            }
        } else {
            return (c ^ d ^ x)
        }
    };
    var r = function (a, c, b) {
        return (a & c) | ((~a) & b)
    };
    var q = function (a, c, b) {
        return (a & b) | (c & (~b))
    };
    var p = function (a, c, b) {
        return (a ^ c ^ b)
    };
    var n = function (a, c, b) {
        return (c ^ (a | (~b)))
    };
    var u = function (W, V, aa, Z, k, X, Y) {
        W = H(W, H(H(r(V, aa, Z), k), Y));
        return H(w(W, X), V)
    };
    var f = function (W, V, aa, Z, k, X, Y) {
        W = H(W, H(H(q(V, aa, Z), k), Y));
        return H(w(W, X), V)
    };
    var F = function (W, V, aa, Z, k, X, Y) {
        W = H(W, H(H(p(V, aa, Z), k), Y));
        return H(w(W, X), V)
    };
    var t = function (W, V, aa, Z, k, X, Y) {
        W = H(W, H(H(n(V, aa, Z), k), Y));
        return H(w(W, X), V)
    };
    var e = function (V) {
        var W;
        var d = V.length;
        var c = d + 8;
        var b = (c - (c % 64)) / 64;
        var x = (b + 1) * 16;
        var X = new Array(x - 1);
        var a = 0;
        var k = 0;
        while (k < d) {
            W = (k - (k % 4)) / 4;
            a = (k % 4) * 8;
            X[W] = (X[W] | (V.charCodeAt(k) << a));
            k++
        }
        W = (k - (k % 4)) / 4;
        a = (k % 4) * 8;
        X[W] = X[W] | (128 << a);
        X[x - 2] = d << 3;
        X[x - 1] = d >>> 29;
        return X
    };
    var s = function (d) {
        var a = "", b = "", k, c;
        for (c = 0; c <= 3; c++) {
            k = (d >>> (c * 8)) & 255;
            b = "0" + k.toString(16);
            a = a + b.substr(b.length - 2, 2)
        }
        return a
    };
    var E = [], L, h, G, v, g, U, T, S, R, O = 7, M = 12, J = 17, I = 22, B = 5, A = 9, z = 14, y = 20, o = 4, m = 11,
        l = 16, j = 23, Q = 6, P = 10, N = 15, K = 21;
    C = this.utf8_encode(C);
    E = e(C);
    U = 1732584193;
    T = 4023233417;
    S = 2562383102;
    R = 271733878;
    D = E.length;
    for (L = 0; L < D; L += 16) {
        h = U;
        G = T;
        v = S;
        g = R;
        U = u(U, T, S, R, E[L + 0], O, 3614090360);
        R = u(R, U, T, S, E[L + 1], M, 3905402710);
        S = u(S, R, U, T, E[L + 2], J, 606105819);
        T = u(T, S, R, U, E[L + 3], I, 3250441966);
        U = u(U, T, S, R, E[L + 4], O, 4118548399);
        R = u(R, U, T, S, E[L + 5], M, 1200080426);
        S = u(S, R, U, T, E[L + 6], J, 2821735955);
        T = u(T, S, R, U, E[L + 7], I, 4249261313);
        U = u(U, T, S, R, E[L + 8], O, 1770035416);
        R = u(R, U, T, S, E[L + 9], M, 2336552879);
        S = u(S, R, U, T, E[L + 10], J, 4294925233);
        T = u(T, S, R, U, E[L + 11], I, 2304563134);
        U = u(U, T, S, R, E[L + 12], O, 1804603682);
        R = u(R, U, T, S, E[L + 13], M, 4254626195);
        S = u(S, R, U, T, E[L + 14], J, 2792965006);
        T = u(T, S, R, U, E[L + 15], I, 1236535329);
        U = f(U, T, S, R, E[L + 1], B, 4129170786);
        R = f(R, U, T, S, E[L + 6], A, 3225465664);
        S = f(S, R, U, T, E[L + 11], z, 643717713);
        T = f(T, S, R, U, E[L + 0], y, 3921069994);
        U = f(U, T, S, R, E[L + 5], B, 3593408605);
        R = f(R, U, T, S, E[L + 10], A, 38016083);
        S = f(S, R, U, T, E[L + 15], z, 3634488961);
        T = f(T, S, R, U, E[L + 4], y, 3889429448);
        U = f(U, T, S, R, E[L + 9], B, 568446438);
        R = f(R, U, T, S, E[L + 14], A, 3275163606);
        S = f(S, R, U, T, E[L + 3], z, 4107603335);
        T = f(T, S, R, U, E[L + 8], y, 1163531501);
        U = f(U, T, S, R, E[L + 13], B, 2850285829);
        R = f(R, U, T, S, E[L + 2], A, 4243563512);
        S = f(S, R, U, T, E[L + 7], z, 1735328473);
        T = f(T, S, R, U, E[L + 12], y, 2368359562);
        U = F(U, T, S, R, E[L + 5], o, 4294588738);
        R = F(R, U, T, S, E[L + 8], m, 2272392833);
        S = F(S, R, U, T, E[L + 11], l, 1839030562);
        T = F(T, S, R, U, E[L + 14], j, 4259657740);
        U = F(U, T, S, R, E[L + 1], o, 2763975236);
        R = F(R, U, T, S, E[L + 4], m, 1272893353);
        S = F(S, R, U, T, E[L + 7], l, 4139469664);
        T = F(T, S, R, U, E[L + 10], j, 3200236656);
        U = F(U, T, S, R, E[L + 13], o, 681279174);
        R = F(R, U, T, S, E[L + 0], m, 3936430074);
        S = F(S, R, U, T, E[L + 3], l, 3572445317);
        T = F(T, S, R, U, E[L + 6], j, 76029189);
        U = F(U, T, S, R, E[L + 9], o, 3654602809);
        R = F(R, U, T, S, E[L + 12], m, 3873151461);
        S = F(S, R, U, T, E[L + 15], l, 530742520);
        T = F(T, S, R, U, E[L + 2], j, 3299628645);
        U = t(U, T, S, R, E[L + 0], Q, 4096336452);
        R = t(R, U, T, S, E[L + 7], P, 1126891415);
        S = t(S, R, U, T, E[L + 14], N, 2878612391);
        T = t(T, S, R, U, E[L + 5], K, 4237533241);
        U = t(U, T, S, R, E[L + 12], Q, 1700485571);
        R = t(R, U, T, S, E[L + 3], P, 2399980690);
        S = t(S, R, U, T, E[L + 10], N, 4293915773);
        T = t(T, S, R, U, E[L + 1], K, 2240044497);
        U = t(U, T, S, R, E[L + 8], Q, 1873313359);
        R = t(R, U, T, S, E[L + 15], P, 4264355552);
        S = t(S, R, U, T, E[L + 6], N, 2734768916);
        T = t(T, S, R, U, E[L + 13], K, 1309151649);
        U = t(U, T, S, R, E[L + 4], Q, 4149444226);
        R = t(R, U, T, S, E[L + 11], P, 3174756917);
        S = t(S, R, U, T, E[L + 2], N, 718787259);
        T = t(T, S, R, U, E[L + 9], K, 3951481745);
        U = H(U, h);
        T = H(T, G);
        S = H(S, v);
        R = H(R, g)
    }
    var i = s(U) + s(T) + s(S) + s(R);
    return i.toLowerCase()
};

function sha1(r) {
    var c = function (w, j) {
        var i = (w << j) | (w >>> (32 - j));
        return i
    };
    var s = function (y) {
        var x = "";
        var w;
        var j;
        for (w = 7; w >= 0; w--) {
            j = (y >>> (w * 4)) & 15;
            x += j.toString(16)
        }
        return x
    };
    var f;
    var u, t;
    var b = new Array(80);
    var l = 1732584193;
    var h = 4023233417;
    var g = 2562383102;
    var e = 271733878;
    var d = 3285377520;
    var q, p, o, n, m;
    var v;
    r = unescape(encodeURIComponent(r));
    var a = r.length;
    var k = [];
    for (u = 0; u < a - 3; u += 4) {
        t = r.charCodeAt(u) << 24 | r.charCodeAt(u + 1) << 16 | r.charCodeAt(u + 2) << 8 | r.charCodeAt(u + 3);
        k.push(t)
    }
    switch (a % 4) {
        case 0:
            u = 2147483648;
            break;
        case 1:
            u = r.charCodeAt(a - 1) << 24 | 8388608;
            break;
        case 2:
            u = r.charCodeAt(a - 2) << 24 | r.charCodeAt(a - 1) << 16 | 32768;
            break;
        case 3:
            u = r.charCodeAt(a - 3) << 24 | r.charCodeAt(a - 2) << 16 | r.charCodeAt(a - 1) << 8 | 128;
            break
    }
    k.push(u);
    while ((k.length % 16) != 14) {
        k.push(0)
    }
    k.push(a >>> 29);
    k.push((a << 3) & 4294967295);
    for (f = 0; f < k.length; f += 16) {
        for (u = 0; u < 16; u++) {
            b[u] = k[f + u]
        }
        for (u = 16; u <= 79; u++) {
            b[u] = c(b[u - 3] ^ b[u - 8] ^ b[u - 14] ^ b[u - 16], 1)
        }
        q = l;
        p = h;
        o = g;
        n = e;
        m = d;
        for (u = 0; u <= 19; u++) {
            v = (c(q, 5) + ((p & o) | (~p & n)) + m + b[u] + 1518500249) & 4294967295;
            m = n;
            n = o;
            o = c(p, 30);
            p = q;
            q = v
        }
        for (u = 20; u <= 39; u++) {
            v = (c(q, 5) + (p ^ o ^ n) + m + b[u] + 1859775393) & 4294967295;
            m = n;
            n = o;
            o = c(p, 30);
            p = q;
            q = v
        }
        for (u = 40; u <= 59; u++) {
            v = (c(q, 5) + ((p & o) | (p & n) | (o & n)) + m + b[u] + 2400959708) & 4294967295;
            m = n;
            n = o;
            o = c(p, 30);
            p = q;
            q = v
        }
        for (u = 60; u <= 79; u++) {
            v = (c(q, 5) + (p ^ o ^ n) + m + b[u] + 3395469782) & 4294967295;
            m = n;
            n = o;
            o = c(p, 30);
            p = q;
            q = v
        }
        l = (l + q) & 4294967295;
        h = (h + p) & 4294967295;
        g = (g + o) & 4294967295;
        e = (e + n) & 4294967295;
        d = (d + m) & 4294967295
    }
    v = s(l) + s(h) + s(g) + s(e) + s(d);
    return v.toLowerCase()
};

//�ַ���
function echo() {
    var o = Array.prototype.slice.call(arguments);
    return console.log(o.join(" "))
};

function log(arr) {
    console.log(arr);
}

function dump(r, n) {
    var t = "", o = function (r, n) {
        for (var t = "", o = 0; o < r; o++) t += n;
        return t
    }, e = function (r, n, t, c) {
        n > 0 && n++;
        var i = o(t * n, c), u = o(t * (n + 1), c), a = "";
        if ("object" == typeof r && null !== r && r.constructor) {
            a += "Array\n" + i + "(\n";
            for (var f in r) "[object Array]" === Object.prototype.toString.call(r[f]) ? (a += u, a += "[", a += f, a += "] => ", a += e(r[f], n + 1, t, c)) : (a += u, a += "[", a += f, a += "] => ", a += r[f], a += "\n");
            a += i + ")\n"
        } else a = null === r || void 0 === r ? "" : r.toString();
        return a
    };
    return t = e(r, 0, 4, " "), !0 !== n ? (echo(t), !0) : t
}

//�ַ�����
function trim(r, n) {
    var t = [" ", "\n", "\r", "\t", "\f", "\v", " ", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "?", "\u2028", "\u2029", "��"].join(""),
        e = 0, i = 0;
    for (r += "", n && (t = (n + "").replace(/([[\]().?\/*{}+$^:])/g, "$1")), e = r.length, i = 0; i < e; i++) if (-1 === t.indexOf(r.charAt(i))) {
        r = r.substring(i);
        break
    }
    for (e = r.length, i = e - 1; i >= 0; i--) if (-1 === t.indexOf(r.charAt(i))) {
        r = r.substring(0, i + 1);
        break
    }
    return -1 === t.indexOf(r.charAt(0)) ? r : ""
}

function rtrim(e, r) {
    return r = r ? (r + "").replace(/([[\]().?\/*{}+$^:])/g, "\\$1") : " \\s ", (e + "").replace(new RegExp("[" + r + "]+$", "g"), "")
}

function ltrim(e, r) {
    return r = r ? (r + "").replace(/([[\]().?\/*{}+$^:])/g, "$1") : " \\s ", (e + "").replace(new RegExp("^[" + r + "]+", "g"), "")
}

function strtrim(a) {
    return a.replace(/\s+/g, " ");
};

function str_replace(t, o, e, c) {
    var r = 0, l = 0, n = "", a = "", i = 0, p = 0, u = [].concat(t), f = [].concat(o), g = e,
        y = "[object Array]" === Object.prototype.toString.call(f),
        b = "[object Array]" === Object.prototype.toString.call(g);
    g = [].concat(g);
    var j = "undefined" != typeof window ? window : global;
    j.$locutus = j.$locutus || {};
    var v = j.$locutus;
    if (v.php = v.php || {}, "object" == typeof t && "string" == typeof o) {
        for (n = o, o = [], r = 0; r < t.length; r += 1) o[r] = n;
        n = "", f = [].concat(o), y = "[object Array]" === Object.prototype.toString.call(f)
    }
    for (void 0 !== c && (c.value = 0), r = 0, i = g.length; r < i; r++) if ("" !== g[r]) for (l = 0, p = u.length; l < p; l++) n = g[r] + "", a = y ? void 0 !== f[l] ? f[l] : "" : f[0], g[r] = n.split(u[l]).join(a), void 0 !== c && (c.value += n.split(u[l]).length - 1);
    return b ? g : g[0]
}

function strip_tags(input, allowed) {
    allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join("");
    var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi, commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
    return input.replace(commentsAndPhpTags, "").replace(tags, function ($0, $1) {
        return allowed.indexOf("<" + $1.toLowerCase() + ">") > -1 ? $0 : ""
    })
};

function strlen(t) {
    var n = t + "";
    return n.length
}

function strtolower(t) {
    return (t + "").toLowerCase()
}

function strtoupper(t) {
    return (t + "").toUpperCase()
}

function ucfirst(t) {
    return t += "", t.charAt(0).toUpperCase() + t.substr(1)
}

function explode(e, t, n) {
    if (arguments.length < 2 || void 0 === e || void 0 === t) return null;
    if ("" === e || !1 === e || null === e) return !1;
    if ("function" == typeof e || "object" == typeof e || "function" == typeof t || "object" == typeof t) return {0: ""};
    !0 === e && (e = "1"), e += "", t += "";
    var o = t.split(e);
    return void 0 === n ? o : (0 === n && (n = 1), n > 0 ? n >= o.length ? o : o.slice(0, n - 1).concat([o.slice(n - 1).join(e)]) : -n >= o.length ? [] : (o.splice(o.length + n), o))
}

function implode(t, r) {
    var e = "", o = "", n = "";
    if (1 === arguments.length && (r = t, t = ""), "object" == typeof r) {
        if ("[object Array]" === Object.prototype.toString.call(r)) return r.join(t);
        for (e in r) o += n + r[e], n = t;
        return o
    }
    return r
}

function str2arr() {
    var r = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : [1, 2],
        e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : ",";
    return is_array(r) ? implode(e, r) : explode(e, r)
}

function json2str(oJson) {
    if (typeof (oJson) == typeof (false)) {
        return oJson
    }
    if (oJson == null) {
        return "null"
    }
    if (typeof (oJson) == typeof (0)) {
        return oJson.toString()
    }
    if (typeof (oJson) == typeof ("") || oJson instanceof String) {
        oJson = oJson.toString();
        oJson = oJson.replace(/\\r\\n/, "\\\\r\\\\n");
        oJson = oJson.replace(/\\n/, "\\\\n");
        oJson = oJson.replace(/\\"/, '\\\\"');
        return '"' + oJson + '"'
    }
    if (oJson instanceof Array) {
        var strRet = "[";
        for (var i = 0; i < oJson.length; i++) {
            if (strRet.length > 1) {
                strRet += ","
            }
            strRet += json2str(oJson[i])
        }
        strRet += "]";
        return strRet
    }
    if (typeof (oJson) == typeof ({})) {
        var strRet = "{";
        for (var p in oJson) {
            if (strRet.length > 1) {
                strRet += ","
            }
            strRet += '"' + p.toString() + '":' + json2str(oJson[p])
        }
        strRet += "}";
        return strRet
    }
};

function str2json(str) {
    return JSON.stringify(str)
};

function htmlspecialchars_decode(e, E) {
    var T = 0, _ = 0, r = !1;
    "undefined" == typeof E && (E = 2), e = e.toString().replace(/&lt;/g, "<").replace(/&gt;/g, ">");
    var t = {
        ENT_NOQUOTES: 0,
        ENT_HTML_QUOTE_SINGLE: 1,
        ENT_HTML_QUOTE_DOUBLE: 2,
        ENT_COMPAT: 2,
        ENT_QUOTES: 3,
        ENT_IGNORE: 4
    };
    if (0 === E && (r = !0), "number" != typeof E) {
        for (E = [].concat(E), _ = 0; _ < E.length; _++) 0 === t[E[_]] ? r = !0 : t[E[_]] && (T |= t[E[_]]);
        E = T
    }
    return E & t.ENT_HTML_QUOTE_SINGLE && (e = e.replace(/&#0*39;/g, "'")), r || (e = e.replace(/&quot;/g, '"')), e = e.replace(/&amp;/g, "&")
}

function htmlspecialchars(e, E, T, _) {
    var r = 0, t = 0, a = !1;
    ("undefined" == typeof E || null === E) && (E = 2), e = e.toString(), _ !== !1 && (e = e.replace(/&/g, "&amp;")), e = e.replace(/</g, "&lt;").replace(/>/g, "&gt;");
    var N = {
        ENT_NOQUOTES: 0,
        ENT_HTML_QUOTE_SINGLE: 1,
        ENT_HTML_QUOTE_DOUBLE: 2,
        ENT_COMPAT: 2,
        ENT_QUOTES: 3,
        ENT_IGNORE: 4
    };
    if (0 === E && (a = !0), "number" != typeof E) {
        for (E = [].concat(E), t = 0; t < E.length; t++) 0 === N[E[t]] ? a = !0 : N[E[t]] && (r |= N[E[t]]);
        E = r
    }
    return E & N.ENT_HTML_QUOTE_SINGLE && (e = e.replace(/'/g, "&#039;")), a || (e = e.replace(/"/g, "&quot;")), e
};

function htmlencode(sStr) {
    return htmlspecialchars(sStr);
};

function htmldecode(sStr) {
    return htmlspecialchars_decode(sStr)
};

function foreach(a, d) {
    var b, c, e;
    if (a && typeof a === "object" && a.change_key_case) {
        return a.foreach(d)
    }
    if (typeof this.Iterator !== "undefined") {
        var c = this.Iterator(a);
        if (d.length === 1) {
            for (e in c) {
                d(e[1])
            }
        } else {
            for (e in c) {
                d(e[0], e[1])
            }
        }
    } else {
        if (d.length === 1) {
            for (b in a) {
                if (a.hasOwnProperty(b)) {
                    d(a[b])
                }
            }
        } else {
            for (b in a) {
                if (a.hasOwnProperty(b)) {
                    d(b, a[b])
                }
            }
        }
    }
};

//����
function preg_match(e, t) {
    return new RegExp(e).test(t)
}

function preg_replace(pattern, replacement, subject, limit) {
    if (typeof limit === 'undefined') limit = -1;
    if (subject.match(eval(pattern))) {
        if (limit == -1) {
            return subject.replace(eval(pattern + 'g'), replacement);
        } else {
            for (x = 0; x < limit; x++) {
                subject = subject.replace(eval(pattern), replacement);
            }
            return subject;
        }
    } else {
        return subject;
    }
}

//url����
function base64_decode(n) {
    var r = function (n) {
        return decodeURIComponent(n.split("").map(function (n) {
            return "%" + ("00" + n.charCodeAt(0).toString(16)).slice(-2)
        }).join(""))
    };
    if ("undefined" == typeof window) return new Buffer(n, "base64").toString("utf-8");
    if (void 0 !== window.atob) return r(window.atob(n));
    var e, t, o, i, d, f, a, c, u = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", h = 0, w = 0,
        C = "", g = [];
    if (!n) return n;
    n += "";
    do {
        i = u.indexOf(n.charAt(h++)), d = u.indexOf(n.charAt(h++)), f = u.indexOf(n.charAt(h++)), a = u.indexOf(n.charAt(h++)), c = i << 18 | d << 12 | f << 6 | a, e = c >> 16 & 255, t = c >> 8 & 255, o = 255 & c, g[w++] = 64 === f ? String.fromCharCode(e) : 64 === a ? String.fromCharCode(e, t) : String.fromCharCode(e, t, o)
    } while (h < n.length);
    return C = g.join(""), r(C.replace(/\0+$/, ""))
}

function base64_encode(e) {
    var r = function (e) {
        return encodeURIComponent(e).replace(/%([0-9A-F]{2})/g, function (e, r) {
            return String.fromCharCode("0x" + r)
        })
    };
    if ("undefined" == typeof window) return new Buffer(e).toString("base64");
    if (void 0 !== window.btoa) return window.btoa(r(e));
    var n, t, o, i, a, c, d, f, h = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", u = 0, w = 0,
        A = "", l = [];
    if (!e) return e;
    e = r(e);
    do {
        n = e.charCodeAt(u++), t = e.charCodeAt(u++), o = e.charCodeAt(u++), f = n << 16 | t << 8 | o, i = f >> 18 & 63, a = f >> 12 & 63, c = f >> 6 & 63, d = 63 & f, l[w++] = h.charAt(i) + h.charAt(a) + h.charAt(c) + h.charAt(d)
    } while (u < e.length);
    A = l.join("");
    var C = e.length % 3;
    return (C ? A.slice(0, C - 3) : A) + "===".slice(C || 3)
}

function urlencode(e) {
    return e += "", encodeURIComponent(e).replace(/!/g, "%21").replace(/'/g, "%27").replace(/\(/g, "%28").replace(/\)/g, "%29").replace(/\*/g, "%2A").replace(/~/g, "%7E").replace(/%20/g, "+")
}

function urldecode(e) {
    return decodeURIComponent((e + "").replace(/%(?![\da-f]{2})/gi, function () {
        return "%25"
    }).replace(/\+/g, "%20"))
}

function utf8_decode(r) {
    var o = [], e = 0, h = 0, t = 0;
    for (r += ""; e < r.length;) {
        h = 255 & r.charCodeAt(e), t = 0, h <= 191 ? (h &= 127, t = 1) : h <= 223 ? (h &= 31, t = 2) : h <= 239 ? (h &= 15, t = 3) : (h &= 7, t = 4);
        for (var n = 1; n < t; ++n) h = h << 6 | 63 & r.charCodeAt(n + e);
        4 === t ? (h -= 65536, o.push(String.fromCharCode(55296 | h >> 10 & 1023)), o.push(String.fromCharCode(56320 | 1023 & h))) : o.push(String.fromCharCode(h)), e += t
    }
    return o.join("")
}

function utf8_encode(r) {
    if (null === r || void 0 === r) return "";
    var e, a, t = r + "", n = "", o = 0;
    e = a = 0, o = t.length;
    for (var i = 0; i < o; i++) {
        var l = t.charCodeAt(i), f = null;
        if (l < 128) a++; else if (l > 127 && l < 2048) f = String.fromCharCode(l >> 6 | 192, 63 & l | 128); else if (55296 != (63488 & l)) f = String.fromCharCode(l >> 12 | 224, l >> 6 & 63 | 128, 63 & l | 128); else {
            if (55296 != (64512 & l)) throw new RangeError("Unmatched trail surrogate at " + i);
            var d = t.charCodeAt(++i);
            if (56320 != (64512 & d)) throw new RangeError("Unmatched lead surrogate at " + (i - 1));
            l = ((1023 & l) << 10) + (1023 & d) + 65536, f = String.fromCharCode(l >> 18 | 240, l >> 12 & 63 | 128, l >> 6 & 63 | 128, 63 & l | 128)
        }
        null !== f && (a > e && (n += t.slice(e, a)), n += f, e = a = i + 1)
    }
    return a > e && (n += t.slice(e, o)), n
}

//��ѧ
function base_convert(n, t, r) {
    return parseInt(n + "", 0 | t).toString(0 | r)
}

function ceil(c) {
    return Math.ceil(c)
}

function floor(o) {
    return Math.floor(o)
}

function mt_rand(r, e) {
    var n = arguments.length;
    if (0 === n) r = 0, e = 2147483647; else {
        if (1 === n) throw new Error("Warning: mt_rand() expects exactly 2 parameters, 1 given");
        r = parseInt(r, 10), e = parseInt(e, 10)
    }
    return Math.floor(Math.random() * (e - r + 1)) + r
}

function rand(r, e) {
    var n = arguments.length;
    if (0 === n) r = 0, e = 2147483647; else if (1 === n) throw new Error("Warning: rand() expects exactly 2 parameters, 1 given");
    return Math.floor(Math.random() * (e - r + 1)) + r
}

function round(a, r, _) {
    var e, t, o, D;
    if (r |= 0, e = Math.pow(10, r), a *= e, D = a > 0 | -(a < 0), o = a % 1 == .5 * D, t = Math.floor(a), o) switch (_) {
        case"PHP_ROUND_HALF_DOWN":
            a = t + (D < 0);
            break;
        case"PHP_ROUND_HALF_EVEN":
            a = t + t % 2 * D;
            break;
        case"PHP_ROUND_HALF_ODD":
            a = t + !(t % 2);
            break;
        default:
            a = t + (D > 0)
    }
    return (o ? a : Math.round(a)) / e
}

function strcut(str, iMaxBytes, sSuffix) {
    if (isNaN(iMaxBytes)) {
        return str
    }
    if (strlen(str) <= iMaxBytes) {
        return str
    }
    var i = 0, bytes = 0;
    for (; i < str.length && bytes < iMaxBytes; ++i, ++bytes) {
        if (str.charCodeAt(i) > 255) {
            ++bytes
        }
    }
    sSuffix = sSuffix || "";
    return (bytes - iMaxBytes == 1 ? str.substr(0, i - 1) : str.substr(0, i)) + sSuffix
};

function strfind(string, find) {
    return !(string.indexOf(find) === -1);
};

function date_eq(strDate1, strDate2) {
    var date1 = new Date(strDate1.replace(/\-/g, "\/"));
    var date2 = new Date(strDate2.replace(/\-/g, "\/"));
    if ((date1 - date2) >= 0) {
        return true;
    } else {
        return false;
    }
}

function timeline(tt) {
    var today = new Date();
    var d = new Date(tt);
    var m = today.getTime() - d.getTime();
    if (m <= 0) {
        m = 1000
    }
    if (m < 60 * 1000) {
        return Math.floor(m / 1000) + "��ǰ"
    } else {
        if (m < 60 * 60 * 1000) {
            return Math.floor(m / (1000 * 60)) + "����ǰ"
        } else {
            if (m < 60 * 60 * 1000 * 24) {
                return Math.floor(m / (1000 * 60 * 60)) + "Сʱǰ"
            } else {
                if (m < 60 * 60 * 1000 * 24 * 7) {
                    return Math.floor(m / (1000 * 60 * 60 * 24)) + "��ǰ"
                } else {
                    if (m < 60 * 60 * 1000 * 24 * 7 * 56) {
                        return Math.floor(m / (1000 * 60 * 60 * 24 * 7)) + "��ǰ"
                    } else {
                        return Math.floor(m / (1000 * 60 * 60 * 24 * 7 * 52)) + "��ǰ"
                    }
                }
            }
        }
    }
};
//��֤����
var is_eq = function (str1, str2) {
    if (str1 == str2) {
        return (true)
    } else {
        return (false)
    }
};
var is_num = function (num) {
    var reg = new RegExp("^[0-9]*$");
    return reg.test(num)
};
var is_phone = function (num) {
    var reg = /^1\d{10}$/;
    return reg.test(num)
};
var is_qq = function (num) {
    var reg = /^[1-utf8_decode]{1}\d{4,11}$/;
    return reg.test(num)
};
var is_email = function (num) {
    var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return reg.test(num)
};
var is_id = function (num) {
    var reg = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
    return reg.test(num)
};
var is_chinese = function (num) {
    var reg = /[\u4e00-\u9fa5]/g;
    return reg.test(num)
};
var is_reg = function (num) {
    var reg = /^([a-zA-z_]{1})([\w]*)$/g;
    return reg.test(num)
};
var is_tel = function (str) {
    var reg = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
    return reg.test(str)
};
var is_ip = function (strIP) {
    if (isNull(strIP)) {
        return false
    }
    var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
    if (re.test(strIP)) {
        if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256) {
            return true
        }
    }
    return false
};
var is_zipcode = function (str) {
    var reg = /^(\d){6}$/;
    return reg.test(str)
};
var is_english = function (str) {
    var reg = /^[A-Za-z]+$/;
    return reg.test(str)
};
var is_url = function (str) {
    var reg = /^http:\/\/[A-Za-z0-9-_]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
    return reg.test(str)
};
var in_int = function (n, iMin, iMax) {
    if (!isFinite(n)) {
        return false
    }
    if (!/^[+-]?\d+$/.test(n)) {
        return false
    }
    if (iMin != undefined && parseInt(n) < parseInt(iMin)) {
        return false
    }
    if (iMax != undefined && parseInt(n) > parseInt(iMax)) {
        return false
    }
    return true
};
var in_float = function (n, fMin, fMax) {
    if (!isFinite(n)) {
        return false
    }
    if (fMin != undefined && parseFloat(n) < parseFloat(fMin)) {
        return false
    }
    if (fMax != undefined && parseFloat(n) > parseFloat(fMax)) {
        return false
    }
    return true
};
var is_http = function (url) {
    if (url.indexOf("http://") === -1 && url.indexOf("https://") === -1) {
        return false
    }
    return true
};

export default {
    time, date, microtime, strtotime, mktime,
    array_merge, array_search, array_keys, array_values, array_slice, array_column, count, in_array, range, sort, ksort,
    is_int, is_float, is_array, is_object, isset, empty, intval, floatval, uniqid,
    md5, sha1, log, dump, trim, ltrim, rtrim, strtrim,
    str_replace, strip_tags, strlen, strtolower, strtoupper, ucfirst, implode, explode, str2arr,
    json2str, str2json, htmlencode, htmldecode, foreach, preg_match, preg_replace,
    utf8_decode, utf8_encode, urlencode, urldecode, base64_decode, base64_encode,
    rand, round,
,
base_convert, floor, ceil, mt_rand,
    strcut, strfind, date_eq, timeline,
    is_eq, is_num, is_phone, is_qq, is_email, is_id, is_chinese, is_reg, is_tel, is_zipcode, is_english, is_url, in_int, in_float, is_http
}