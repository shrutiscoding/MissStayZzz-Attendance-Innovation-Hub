package com.example.hostel_app;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class ByteArrayRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;

    public ByteArrayRequest(int method, String url, Response.Listener<byte[]> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, null);
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }
}
