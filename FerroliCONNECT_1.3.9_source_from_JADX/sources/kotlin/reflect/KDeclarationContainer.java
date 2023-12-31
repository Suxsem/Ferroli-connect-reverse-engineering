package kotlin.reflect;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u001c\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo22149d2 = {"Lkotlin/reflect/KDeclarationContainer;", "", "members", "", "Lkotlin/reflect/KCallable;", "getMembers", "()Ljava/util/Collection;", "kotlin-stdlib"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: KDeclarationContainer.kt */
public interface KDeclarationContainer {
    @NotNull
    Collection<KCallable<?>> getMembers();
}
