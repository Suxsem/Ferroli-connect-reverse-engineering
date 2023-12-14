package anet.channel.p006c;

import anet.channel.util.ALog;
import anetwork.channel.config.IRemoteConfig;
import anetwork.channel.util.RequestConstant;
import com.taobao.orange.OrangeConfig;

/* renamed from: anet.channel.c.a */
/* compiled from: Taobao */
public class C0488a implements IRemoteConfig {

    /* renamed from: a */
    private static boolean f174a = false;

    static {
        try {
            Class.forName("com.taobao.orange.OrangeConfig");
            f174a = true;
        } catch (Exception unused) {
            f174a = false;
        }
    }

    public void register() {
        if (!f174a) {
            ALog.m330w("awcn.OrangeConfigImpl", "no orange sdk", (String) null, new Object[0]);
            return;
        }
        try {
            OrangeConfig.getInstance().registerListener(new String[]{"networkSdk"}, new C0489b(this));
            getConfig("networkSdk", "network_empty_scheme_https_switch", RequestConstant.TRUE);
        } catch (Exception e) {
            ALog.m326e("awcn.OrangeConfigImpl", "register fail", (String) null, e, new Object[0]);
        }
    }

    public void unRegister() {
        if (!f174a) {
            ALog.m330w("awcn.OrangeConfigImpl", "no orange sdk", (String) null, new Object[0]);
        } else {
            OrangeConfig.getInstance().unregisterListener(new String[]{"networkSdk"});
        }
    }

    public String getConfig(String... strArr) {
        if (!f174a) {
            ALog.m330w("awcn.OrangeConfigImpl", "no orange sdk", (String) null, new Object[0]);
            return null;
        }
        try {
            return OrangeConfig.getInstance().getConfig(strArr[0], strArr[1], strArr[2]);
        } catch (Exception e) {
            ALog.m326e("awcn.OrangeConfigImpl", "get config failed!", (String) null, e, new Object[0]);
            return null;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(106:2|3|4|5|6|7|8|9|10|(1:12)|13|14|15|16|17|18|19|20|(1:22)|23|24|25|26|(1:28)|29|30|(1:32)|33|34|35|36|37|38|(2:40|(1:42))|43|44|45|46|(1:48)|49|50|(3:52|(1:54)|55)|56|57|(1:59)|60|61|(1:63)|64|65|(1:67)|68|69|(1:71)|72|73|(1:75)|76|77|(1:79)|80|81|(1:83)|84|85|(1:87)|88|89|(1:91)|92|93|(1:95)|96|97|(1:99)|100|101|(1:103)|104|105|(1:107)|108|109|(1:111)|112|113|(1:115)|116|117|(1:119)|120|121|(1:123)|124|125|(1:127)|128|129|(1:131)|132|133|(1:135)|136|137|(1:139)|(3:140|141|(2:143|145)(1:148))) */
    /* JADX WARNING: Can't wrap try/catch for region: R(108:2|3|4|5|6|7|8|9|10|(1:12)|13|14|15|16|17|18|19|20|(1:22)|23|24|25|26|(1:28)|29|30|(1:32)|33|34|35|36|37|38|(2:40|(1:42))|43|44|45|46|(1:48)|49|50|(3:52|(1:54)|55)|56|57|(1:59)|60|61|(1:63)|64|65|(1:67)|68|69|(1:71)|72|73|(1:75)|76|77|(1:79)|80|81|(1:83)|84|85|(1:87)|88|89|(1:91)|92|93|(1:95)|96|97|(1:99)|100|101|(1:103)|104|105|(1:107)|108|109|(1:111)|112|113|(1:115)|116|117|(1:119)|120|121|(1:123)|124|125|(1:127)|128|129|(1:131)|132|133|(1:135)|136|137|(1:139)|140|141|(2:143|145)(1:148)) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:100:0x0365 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:104:0x0384 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:108:0x03a3 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:112:0x03c2 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:116:0x03e1 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:120:0x0400 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:124:0x0417 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:128:0x0436 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:132:0x0455 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:136:0x0474 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0086 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:140:0x0493 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x009f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x00b8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x00d3 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x00f2 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0103 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x011a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x0131 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x014a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x0163 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x019b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x01b4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x01e4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x0206 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0039 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:60:0x0225 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:64:0x0244 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x025b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:72:0x0272 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:76:0x0291 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0052 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:80:0x02b0 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:84:0x02c7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:88:0x02e6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:92:0x0316 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:96:0x0346 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x006b */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0379 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0398 A[Catch:{ Exception -> 0x03a3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x03b7 A[Catch:{ Exception -> 0x03c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x03d6 A[Catch:{ Exception -> 0x03e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x03f5 A[Catch:{ Exception -> 0x0400 }] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0414 A[Catch:{ Exception -> 0x0417 }] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x042b A[Catch:{ Exception -> 0x0436 }] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x007b A[Catch:{ Exception -> 0x0086 }] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x044a A[Catch:{ Exception -> 0x0455 }] */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0469 A[Catch:{ Exception -> 0x0474 }] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0488 A[Catch:{ Exception -> 0x0493 }] */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x04a7 A[Catch:{ Exception -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:148:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00e7 A[Catch:{ Exception -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0117 A[Catch:{ Exception -> 0x011a }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x012e A[Catch:{ Exception -> 0x0131 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0177 A[Catch:{ Exception -> 0x019b }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01c8 A[Catch:{ Exception -> 0x01e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01f8 A[Catch:{ Exception -> 0x0206 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x021a A[Catch:{ Exception -> 0x0225 }] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0239 A[Catch:{ Exception -> 0x0244 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0258 A[Catch:{ Exception -> 0x025b }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x026f A[Catch:{ Exception -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0286 A[Catch:{ Exception -> 0x0291 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x02a5 A[Catch:{ Exception -> 0x02b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x02c4 A[Catch:{ Exception -> 0x02c7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02db A[Catch:{ Exception -> 0x02e6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x02fa A[Catch:{ Exception -> 0x0316 }] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x032a A[Catch:{ Exception -> 0x0346 }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x035a A[Catch:{ Exception -> 0x0365 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onConfigUpdate(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = "true"
            java.lang.String r1 = "networkSdk"
            boolean r1 = r1.equals(r10)
            if (r1 == 0) goto L_0x04aa
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            java.lang.String r4 = "namespace"
            r2[r3] = r4
            r4 = 1
            r2[r4] = r10
            r5 = 0
            java.lang.String r6 = "awcn.OrangeConfigImpl"
            java.lang.String r7 = "onConfigUpdate"
            anet.channel.util.ALog.m328i(r6, r7, r5, r2)
            r2 = 3
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0039 }
            r6[r3] = r10     // Catch:{ Exception -> 0x0039 }
            java.lang.String r7 = "network_empty_scheme_https_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x0039 }
            r6[r1] = r0     // Catch:{ Exception -> 0x0039 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x0039 }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x0039 }
            anet.channel.strategy.c r7 = anet.channel.strategy.C0566c.C0567a.f482a     // Catch:{ Exception -> 0x0039 }
            r7.mo9041a((boolean) r6)     // Catch:{ Exception -> 0x0039 }
        L_0x0039:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0052 }
            r6[r3] = r10     // Catch:{ Exception -> 0x0052 }
            java.lang.String r7 = "network_spdy_enable_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x0052 }
            r6[r1] = r0     // Catch:{ Exception -> 0x0052 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x0052 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x0052 }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x0052 }
            anetwork.channel.config.NetworkConfigCenter.setSpdyEnabled(r6)     // Catch:{ Exception -> 0x0052 }
        L_0x0052:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x006b }
            r6[r3] = r10     // Catch:{ Exception -> 0x006b }
            java.lang.String r7 = "network_http_cache_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x006b }
            r6[r1] = r0     // Catch:{ Exception -> 0x006b }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x006b }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x006b }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x006b }
            anetwork.channel.config.NetworkConfigCenter.setHttpCacheEnable(r6)     // Catch:{ Exception -> 0x006b }
        L_0x006b:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0086 }
            r6[r3] = r10     // Catch:{ Exception -> 0x0086 }
            java.lang.String r7 = "network_http_cache_flag"
            r6[r4] = r7     // Catch:{ Exception -> 0x0086 }
            r6[r1] = r5     // Catch:{ Exception -> 0x0086 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x0086 }
            if (r6 == 0) goto L_0x0086
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ Exception -> 0x0086 }
            long r6 = r6.longValue()     // Catch:{ Exception -> 0x0086 }
            anetwork.channel.config.NetworkConfigCenter.setCacheFlag(r6)     // Catch:{ Exception -> 0x0086 }
        L_0x0086:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x009f }
            r6[r3] = r10     // Catch:{ Exception -> 0x009f }
            java.lang.String r7 = "network_https_sni_enable_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x009f }
            r6[r1] = r0     // Catch:{ Exception -> 0x009f }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x009f }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x009f }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x009f }
            anet.channel.AwcnConfig.setHttpsSniEnable(r6)     // Catch:{ Exception -> 0x009f }
        L_0x009f:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x00b8 }
            r6[r3] = r10     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r7 = "network_accs_session_bg_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x00b8 }
            r6[r1] = r0     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x00b8 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x00b8 }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x00b8 }
            anet.channel.AwcnConfig.setAccsSessionCreateForbiddenInBg(r6)     // Catch:{ Exception -> 0x00b8 }
        L_0x00b8:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x00d3 }
            r6[r3] = r10     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r7 = "network_request_statistic_sample_rate"
            r6[r4] = r7     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r7 = "10000"
            r6[r1] = r7     // Catch:{ Exception -> 0x00d3 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x00d3 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x00d3 }
            int r6 = r6.intValue()     // Catch:{ Exception -> 0x00d3 }
            anetwork.channel.config.NetworkConfigCenter.setRequestStatisticSampleRate(r6)     // Catch:{ Exception -> 0x00d3 }
        L_0x00d3:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x00f2 }
            r6[r3] = r10     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r7 = "network_request_forbidden_bg"
            r6[r4] = r7     // Catch:{ Exception -> 0x00f2 }
            r6[r1] = r5     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x00f2 }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x00f2 }
            if (r7 != 0) goto L_0x00f2
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x00f2 }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x00f2 }
            anetwork.channel.config.NetworkConfigCenter.setBgRequestForbidden(r6)     // Catch:{ Exception -> 0x00f2 }
        L_0x00f2:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0103 }
            r6[r3] = r10     // Catch:{ Exception -> 0x0103 }
            java.lang.String r7 = "network_url_white_list_bg"
            r6[r4] = r7     // Catch:{ Exception -> 0x0103 }
            r6[r1] = r5     // Catch:{ Exception -> 0x0103 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x0103 }
            anetwork.channel.config.NetworkConfigCenter.updateWhiteListMap(r6)     // Catch:{ Exception -> 0x0103 }
        L_0x0103:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x011a }
            r6[r3] = r10     // Catch:{ Exception -> 0x011a }
            java.lang.String r7 = "network_biz_white_list_bg"
            r6[r4] = r7     // Catch:{ Exception -> 0x011a }
            r6[r1] = r5     // Catch:{ Exception -> 0x011a }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x011a }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x011a }
            if (r7 != 0) goto L_0x011a
            anetwork.channel.config.NetworkConfigCenter.updateBizWhiteList(r6)     // Catch:{ Exception -> 0x011a }
        L_0x011a:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0131 }
            r6[r3] = r10     // Catch:{ Exception -> 0x0131 }
            java.lang.String r7 = "network_amdc_preset_hosts"
            r6[r4] = r7     // Catch:{ Exception -> 0x0131 }
            r6[r1] = r5     // Catch:{ Exception -> 0x0131 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x0131 }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x0131 }
            if (r7 != 0) goto L_0x0131
            anetwork.channel.config.NetworkConfigCenter.setAmdcPresetHosts(r6)     // Catch:{ Exception -> 0x0131 }
        L_0x0131:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x014a }
            r6[r3] = r10     // Catch:{ Exception -> 0x014a }
            java.lang.String r7 = "network_horse_race_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x014a }
            r6[r1] = r0     // Catch:{ Exception -> 0x014a }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x014a }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x014a }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x014a }
            anet.channel.AwcnConfig.setHorseRaceEnable(r6)     // Catch:{ Exception -> 0x014a }
        L_0x014a:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0163 }
            r6[r3] = r10     // Catch:{ Exception -> 0x0163 }
            java.lang.String r7 = "tnet_enable_header_cache"
            r6[r4] = r7     // Catch:{ Exception -> 0x0163 }
            r6[r1] = r0     // Catch:{ Exception -> 0x0163 }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x0163 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x0163 }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x0163 }
            anet.channel.AwcnConfig.setTnetHeaderCacheEnable(r6)     // Catch:{ Exception -> 0x0163 }
        L_0x0163:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x019b }
            r6[r3] = r10     // Catch:{ Exception -> 0x019b }
            java.lang.String r7 = "network_http3_enable_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x019b }
            r6[r1] = r5     // Catch:{ Exception -> 0x019b }
            java.lang.String r6 = r9.getConfig(r6)     // Catch:{ Exception -> 0x019b }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x019b }
            if (r7 != 0) goto L_0x019b
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x019b }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x019b }
            android.content.Context r7 = anetwork.channel.http.NetworkSdkSetting.getContext()     // Catch:{ Exception -> 0x019b }
            android.content.SharedPreferences r7 = android.preference.PreferenceManager.getDefaultSharedPreferences(r7)     // Catch:{ Exception -> 0x019b }
            android.content.SharedPreferences$Editor r7 = r7.edit()     // Catch:{ Exception -> 0x019b }
            java.lang.String r8 = "HTTP3_ENABLE"
            r7.putBoolean(r8, r6)     // Catch:{ Exception -> 0x019b }
            r7.apply()     // Catch:{ Exception -> 0x019b }
            anet.channel.AwcnConfig.setHttp3OrangeEnable(r6)     // Catch:{ Exception -> 0x019b }
            if (r6 != 0) goto L_0x019b
            anet.channel.AwcnConfig.setHttp3Enable(r3)     // Catch:{ Exception -> 0x019b }
        L_0x019b:
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x01b4 }
            r6[r3] = r10     // Catch:{ Exception -> 0x01b4 }
            java.lang.String r7 = "network_response_buffer_switch"
            r6[r4] = r7     // Catch:{ Exception -> 0x01b4 }
            r6[r1] = r0     // Catch:{ Exception -> 0x01b4 }
            java.lang.String r0 = r9.getConfig(r6)     // Catch:{ Exception -> 0x01b4 }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x01b4 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x01b4 }
            anetwork.channel.config.NetworkConfigCenter.setResponseBufferEnable(r0)     // Catch:{ Exception -> 0x01b4 }
        L_0x01b4:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x01e4 }
            r0[r3] = r10     // Catch:{ Exception -> 0x01e4 }
            java.lang.String r6 = "network_get_session_async_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x01e4 }
            r0[r1] = r5     // Catch:{ Exception -> 0x01e4 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x01e4 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x01e4 }
            if (r6 != 0) goto L_0x01e4
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x01e4 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x01e4 }
            android.content.Context r6 = anetwork.channel.http.NetworkSdkSetting.getContext()     // Catch:{ Exception -> 0x01e4 }
            android.content.SharedPreferences r6 = android.preference.PreferenceManager.getDefaultSharedPreferences(r6)     // Catch:{ Exception -> 0x01e4 }
            android.content.SharedPreferences$Editor r6 = r6.edit()     // Catch:{ Exception -> 0x01e4 }
            java.lang.String r7 = "SESSION_ASYNC_OPTIMIZE"
            r6.putBoolean(r7, r0)     // Catch:{ Exception -> 0x01e4 }
            r6.apply()     // Catch:{ Exception -> 0x01e4 }
        L_0x01e4:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0206 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0206 }
            java.lang.String r6 = "network_bg_forbid_request_threshold"
            r0[r4] = r6     // Catch:{ Exception -> 0x0206 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0206 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0206 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0206 }
            if (r6 != 0) goto L_0x0206
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0206 }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x0206 }
            if (r0 >= 0) goto L_0x0203
            r0 = 0
        L_0x0203:
            anetwork.channel.config.NetworkConfigCenter.setBgForbidRequestThreshold(r0)     // Catch:{ Exception -> 0x0206 }
        L_0x0206:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0225 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0225 }
            java.lang.String r6 = "network_normal_thread_pool_executor_size"
            r0[r4] = r6     // Catch:{ Exception -> 0x0225 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0225 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0225 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0225 }
            if (r6 != 0) goto L_0x0225
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0225 }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x0225 }
            anet.channel.thread.ThreadPoolExecutorFactory.setNormalExecutorPoolSize(r0)     // Catch:{ Exception -> 0x0225 }
        L_0x0225:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0244 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0244 }
            java.lang.String r6 = "network_idle_session_close_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x0244 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0244 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0244 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0244 }
            if (r6 != 0) goto L_0x0244
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0244 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0244 }
            anet.channel.AwcnConfig.setIdleSessionCloseEnable(r0)     // Catch:{ Exception -> 0x0244 }
        L_0x0244:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x025b }
            r0[r3] = r10     // Catch:{ Exception -> 0x025b }
            java.lang.String r6 = "network_monitor_requests"
            r0[r4] = r6     // Catch:{ Exception -> 0x025b }
            r0[r1] = r5     // Catch:{ Exception -> 0x025b }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x025b }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x025b }
            if (r6 != 0) goto L_0x025b
            anetwork.channel.config.NetworkConfigCenter.setMonitorRequestList(r0)     // Catch:{ Exception -> 0x025b }
        L_0x025b:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0272 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0272 }
            java.lang.String r6 = "network_session_preset_hosts"
            r0[r4] = r6     // Catch:{ Exception -> 0x0272 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0272 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0272 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0272 }
            if (r6 != 0) goto L_0x0272
            anet.channel.AwcnConfig.registerPresetSessions(r0)     // Catch:{ Exception -> 0x0272 }
        L_0x0272:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0291 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0291 }
            java.lang.String r6 = "network_ipv6_blacklist_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x0291 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0291 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0291 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0291 }
            if (r6 != 0) goto L_0x0291
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0291 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0291 }
            anet.channel.AwcnConfig.setIpv6BlackListEnable(r0)     // Catch:{ Exception -> 0x0291 }
        L_0x0291:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x02b0 }
            r0[r3] = r10     // Catch:{ Exception -> 0x02b0 }
            java.lang.String r6 = "network_ipv6_blacklist_ttl"
            r0[r4] = r6     // Catch:{ Exception -> 0x02b0 }
            r0[r1] = r5     // Catch:{ Exception -> 0x02b0 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x02b0 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x02b0 }
            if (r6 != 0) goto L_0x02b0
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ Exception -> 0x02b0 }
            long r6 = r0.longValue()     // Catch:{ Exception -> 0x02b0 }
            anet.channel.AwcnConfig.setIpv6BlackListTtl(r6)     // Catch:{ Exception -> 0x02b0 }
        L_0x02b0:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x02c7 }
            r0[r3] = r10     // Catch:{ Exception -> 0x02c7 }
            java.lang.String r6 = "network_url_degrade_list"
            r0[r4] = r6     // Catch:{ Exception -> 0x02c7 }
            r0[r1] = r5     // Catch:{ Exception -> 0x02c7 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x02c7 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x02c7 }
            if (r6 != 0) goto L_0x02c7
            anetwork.channel.config.NetworkConfigCenter.setDegradeRequestList(r0)     // Catch:{ Exception -> 0x02c7 }
        L_0x02c7:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x02e6 }
            r0[r3] = r10     // Catch:{ Exception -> 0x02e6 }
            java.lang.String r6 = "network_delay_retry_request_no_network"
            r0[r4] = r6     // Catch:{ Exception -> 0x02e6 }
            r0[r1] = r5     // Catch:{ Exception -> 0x02e6 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x02e6 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x02e6 }
            if (r6 != 0) goto L_0x02e6
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x02e6 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x02e6 }
            anetwork.channel.config.NetworkConfigCenter.setRequestDelayRetryForNoNetwork(r0)     // Catch:{ Exception -> 0x02e6 }
        L_0x02e6:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0316 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0316 }
            java.lang.String r6 = "network_bind_service_optimize"
            r0[r4] = r6     // Catch:{ Exception -> 0x0316 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0316 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0316 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0316 }
            if (r6 != 0) goto L_0x0316
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0316 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0316 }
            android.content.Context r6 = anetwork.channel.http.NetworkSdkSetting.getContext()     // Catch:{ Exception -> 0x0316 }
            android.content.SharedPreferences r6 = android.preference.PreferenceManager.getDefaultSharedPreferences(r6)     // Catch:{ Exception -> 0x0316 }
            android.content.SharedPreferences$Editor r6 = r6.edit()     // Catch:{ Exception -> 0x0316 }
            java.lang.String r7 = "SERVICE_OPTIMIZE"
            r6.putBoolean(r7, r0)     // Catch:{ Exception -> 0x0316 }
            r6.apply()     // Catch:{ Exception -> 0x0316 }
        L_0x0316:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0346 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0346 }
            java.lang.String r6 = "network_forbid_next_launch_optimize"
            r0[r4] = r6     // Catch:{ Exception -> 0x0346 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0346 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0346 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0346 }
            if (r6 != 0) goto L_0x0346
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0346 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0346 }
            android.content.Context r6 = anetwork.channel.http.NetworkSdkSetting.getContext()     // Catch:{ Exception -> 0x0346 }
            android.content.SharedPreferences r6 = android.preference.PreferenceManager.getDefaultSharedPreferences(r6)     // Catch:{ Exception -> 0x0346 }
            android.content.SharedPreferences$Editor r6 = r6.edit()     // Catch:{ Exception -> 0x0346 }
            java.lang.String r7 = "NEXT_LAUNCH_FORBID"
            r6.putBoolean(r7, r0)     // Catch:{ Exception -> 0x0346 }
            r6.apply()     // Catch:{ Exception -> 0x0346 }
        L_0x0346:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0365 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0365 }
            java.lang.String r6 = "network_detect_enable_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x0365 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0365 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0365 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0365 }
            if (r6 != 0) goto L_0x0365
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0365 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0365 }
            anet.channel.AwcnConfig.setNetworkDetectEnable(r0)     // Catch:{ Exception -> 0x0365 }
        L_0x0365:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0384 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0384 }
            java.lang.String r6 = "network_ping6_enable_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x0384 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0384 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0384 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0384 }
            if (r6 != 0) goto L_0x0384
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0384 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0384 }
            anet.channel.AwcnConfig.setPing6Enable(r0)     // Catch:{ Exception -> 0x0384 }
        L_0x0384:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x03a3 }
            r0[r3] = r10     // Catch:{ Exception -> 0x03a3 }
            java.lang.String r6 = "network_ipv6_global_enable_swtich"
            r0[r4] = r6     // Catch:{ Exception -> 0x03a3 }
            r0[r1] = r5     // Catch:{ Exception -> 0x03a3 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x03a3 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x03a3 }
            if (r6 != 0) goto L_0x03a3
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x03a3 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x03a3 }
            anet.channel.AwcnConfig.setIpv6Enable(r0)     // Catch:{ Exception -> 0x03a3 }
        L_0x03a3:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x03c2 }
            r0[r3] = r10     // Catch:{ Exception -> 0x03c2 }
            java.lang.String r6 = "network_xquic_cong_control"
            r0[r4] = r6     // Catch:{ Exception -> 0x03c2 }
            r0[r1] = r5     // Catch:{ Exception -> 0x03c2 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x03c2 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x03c2 }
            if (r6 != 0) goto L_0x03c2
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x03c2 }
            int r0 = r0.intValue()     // Catch:{ Exception -> 0x03c2 }
            anet.channel.AwcnConfig.setXquicCongControl(r0)     // Catch:{ Exception -> 0x03c2 }
        L_0x03c2:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x03e1 }
            r0[r3] = r10     // Catch:{ Exception -> 0x03e1 }
            java.lang.String r6 = "network_http3_detect_valid_time"
            r0[r4] = r6     // Catch:{ Exception -> 0x03e1 }
            r0[r1] = r5     // Catch:{ Exception -> 0x03e1 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x03e1 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x03e1 }
            if (r6 != 0) goto L_0x03e1
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ Exception -> 0x03e1 }
            long r6 = r0.longValue()     // Catch:{ Exception -> 0x03e1 }
            anet.channel.p008e.C0508a.m105a((long) r6)     // Catch:{ Exception -> 0x03e1 }
        L_0x03e1:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0400 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0400 }
            java.lang.String r6 = "network_ip_stack_detect_by_udp_connect_enable_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x0400 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0400 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0400 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0400 }
            if (r6 != 0) goto L_0x0400
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0400 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0400 }
            anet.channel.AwcnConfig.setIpStackDetectByUdpConnect(r0)     // Catch:{ Exception -> 0x0400 }
        L_0x0400:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0417 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0417 }
            java.lang.String r6 = "network_cookie_monitor"
            r0[r4] = r6     // Catch:{ Exception -> 0x0417 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0417 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0417 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0417 }
            if (r6 != 0) goto L_0x0417
            anetwork.channel.cookie.CookieManager.setTargetMonitorCookieName(r0)     // Catch:{ Exception -> 0x0417 }
        L_0x0417:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0436 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0436 }
            java.lang.String r6 = "network_cookie_header_redundant_fix"
            r0[r4] = r6     // Catch:{ Exception -> 0x0436 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0436 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0436 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0436 }
            if (r6 != 0) goto L_0x0436
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0436 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0436 }
            anet.channel.AwcnConfig.setCookieHeaderRedundantFix(r0)     // Catch:{ Exception -> 0x0436 }
        L_0x0436:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0455 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0455 }
            java.lang.String r6 = "network_channel_local_instance_enable_switch"
            r0[r4] = r6     // Catch:{ Exception -> 0x0455 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0455 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0455 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0455 }
            if (r6 != 0) goto L_0x0455
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0455 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0455 }
            anetwork.channel.config.NetworkConfigCenter.setChannelLocalInstanceEnable(r0)     // Catch:{ Exception -> 0x0455 }
        L_0x0455:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0474 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0474 }
            java.lang.String r6 = "network_allow_spdy_when_bind_service_failed"
            r0[r4] = r6     // Catch:{ Exception -> 0x0474 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0474 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0474 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0474 }
            if (r6 != 0) goto L_0x0474
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0474 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0474 }
            anetwork.channel.config.NetworkConfigCenter.setAllowSpdyWhenBindServiceFailed(r0)     // Catch:{ Exception -> 0x0474 }
        L_0x0474:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0493 }
            r0[r3] = r10     // Catch:{ Exception -> 0x0493 }
            java.lang.String r6 = "network_send_connect_info_by_service"
            r0[r4] = r6     // Catch:{ Exception -> 0x0493 }
            r0[r1] = r5     // Catch:{ Exception -> 0x0493 }
            java.lang.String r0 = r9.getConfig(r0)     // Catch:{ Exception -> 0x0493 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0493 }
            if (r6 != 0) goto L_0x0493
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x0493 }
            boolean r0 = r0.booleanValue()     // Catch:{ Exception -> 0x0493 }
            anet.channel.AwcnConfig.setSendConnectInfoByService(r0)     // Catch:{ Exception -> 0x0493 }
        L_0x0493:
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ Exception -> 0x04aa }
            r0[r3] = r10     // Catch:{ Exception -> 0x04aa }
            java.lang.String r10 = "network_http_dns_notify_white_list"
            r0[r4] = r10     // Catch:{ Exception -> 0x04aa }
            r0[r1] = r5     // Catch:{ Exception -> 0x04aa }
            java.lang.String r10 = r9.getConfig(r0)     // Catch:{ Exception -> 0x04aa }
            boolean r0 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x04aa }
            if (r0 != 0) goto L_0x04aa
            anet.channel.AwcnConfig.setHttpDnsNotifyWhiteList(r10)     // Catch:{ Exception -> 0x04aa }
        L_0x04aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.p006c.C0488a.onConfigUpdate(java.lang.String):void");
    }
}
